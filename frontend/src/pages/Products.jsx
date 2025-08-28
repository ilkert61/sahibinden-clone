// src/pages/Products.jsx
import { useEffect, useMemo, useState } from "react";
import { fetchProducts } from "../services/products";
import Card from "../components/ui/Card";

export default function Products() {
    const [items, setItems] = useState([]);
    const [loading, setLoading] = useState(true);

    // Sıralama
    const [sort, setSort] = useState({ key: "productionyear", dir: "desc" });
    const toggleSort = (key) => {
        setSort((s) =>
            s.key === key ? { key, dir: s.dir === "asc" ? "desc" : "asc" } : { key, dir: "asc" }
        );
    };
    const caret = (k) => (sort.key === k ? (sort.dir === "asc" ? "▲" : "▼") : "◇");

    // Filtreler
    const [q, setQ] = useState({
        brand: "", model: "", series: "",
        fuelType: "", transmissionType: "", color: "",
        minYear: "", maxYear: "", minKm: "", maxKm: "",
        minPrice: "", maxPrice: "", hasAccidentRecord: "any",
        onlyWithImage: false,
    });
    const onChange = (e) => {
        const { name, value, type, checked } = e.target;
        setQ((p) => ({ ...p, [name]: type === "checkbox" ? checked : value }));
    };
    const resetFilters = () => {
        setQ({
            brand: "", model: "", series: "",
            fuelType: "", transmissionType: "", color: "",
            minYear: "", maxYear: "", minKm: "", maxKm: "",
            minPrice: "", maxPrice: "", hasAccidentRecord: "any",
            onlyWithImage: false,
        });
    };

    useEffect(() => {
        (async () => {
            setLoading(true);
            try {
                const data = await fetchProducts(0, 300);
                setItems(data);
            } finally {
                setLoading(false);
            }
        })();
    }, []);

    const includes = (a, b) =>
        (a || "").toString().toLowerCase().includes((b || "").toString().toLowerCase());
    const toNum = (v) => (v === "" || v === null || v === undefined ? null : Number(v));
    const fmtNum = (n) =>
        n === null || n === undefined ? "—" : Number(n).toLocaleString("tr-TR");

    const data = useMemo(() => {
        const minYear = toNum(q.minYear), maxYear = toNum(q.maxYear);
        const minKm = toNum(q.minKm), maxKm = toNum(q.maxKm);
        const minPrice = toNum(q.minPrice), maxPrice = toNum(q.maxPrice);

        let arr = items.filter((p) => {
            const year = p.productionyear ?? null;
            const km = p.mileage ?? null;
            const price = p.price ?? null;

            if (q.onlyWithImage && !p.imageUrl) return false;
            if (q.brand && !includes(p.brand, q.brand)) return false;
            if (q.model && !includes(p.model, q.model)) return false;
            if (q.series && !includes(p.series, q.series)) return false;

            if (q.fuelType && (p.fueltype || "").toLowerCase() !== q.fuelType.toLowerCase()) return false;
            if (q.transmissionType && (p.transmissiontype || "").toLowerCase() !== q.transmissionType.toLowerCase()) return false;
            if (q.color && (p.color || "").toLowerCase() !== q.color.toLowerCase()) return false;

            if (minYear !== null && year !== null && year < minYear) return false;
            if (maxYear !== null && year !== null && year > maxYear) return false;
            if (minKm !== null && km !== null && km < minKm) return false;
            if (maxKm !== null && km !== null && km > maxKm) return false;
            if (minPrice !== null && price !== null && price < minPrice) return false;
            if (maxPrice !== null && price !== null && price > maxPrice) return false;

            if (q.hasAccidentRecord === "yes" && p.hasaccidentrecord !== true) return false;
            if (q.hasAccidentRecord === "no" && p.hasaccidentrecord !== false) return false;

            return true;
        });

        const dir = sort.dir === "asc" ? 1 : -1;
        arr.sort((a, b) => {
            const A = a?.[sort.key];
            const B = b?.[sort.key];
            if (["productionyear", "mileage", "price"].includes(sort.key)) {
                const nA = Number(A ?? 0), nB = Number(B ?? 0);
                return (nA - nB) * dir;
            }
            return String(A ?? "").localeCompare(String(B ?? ""), "tr") * dir;
        });

        return arr;
    }, [items, q, sort]);

    if (loading) return <div className="p-4">Yükleniyor…</div>;

    return (
        <div className="max-w-6xl mx-auto p-4">
            <div className="flex items-center justify-between mb-3">
                <h2 className="text-xl font-bold">İlanlar ({data.length})</h2>
            </div>

            <div className="md:flex md:gap-4">
                {/* SOL: Filteler (biraz küçültülmüş) */}
                <Card className="p-3 md:w-56 md:shrink-0 mb-4 md:mb-0 md:sticky md:top-4 h-fit text-[13px]">
                    <div className="space-y-2">
                        <input className="h-9 w-full px-2 rounded-lg border border-gray-300 bg-white"
                               placeholder="Marka" name="brand" value={q.brand} onChange={onChange}/>
                        <input className="h-9 w-full px-2 rounded-lg border border-gray-300 bg-white"
                               placeholder="Model" name="model" value={q.model} onChange={onChange}/>
                        <input className="h-9 w-full px-2 rounded-lg border border-gray-300 bg-white"
                               placeholder="Seri" name="series" value={q.series} onChange={onChange}/>

                        <select className="h-9 w-full px-2 rounded-lg border border-gray-300 bg-white"
                                name="fuelType" value={q.fuelType} onChange={onChange}>
                            <option value="">Yakıt (hepsi)</option>
                            <option value="Benzin">Benzin</option>
                            <option value="Dizel">Dizel</option>
                            <option value="LPG">LPG</option>
                            <option value="Hybrid">Hybrid</option>
                            <option value="Elektrik">Elektrik</option>
                        </select>

                        <select className="h-9 w-full px-2 rounded-lg border border-gray-300 bg-white"
                                name="transmissionType" value={q.transmissionType} onChange={onChange}>
                            <option value="">Vites (hepsi)</option>
                            <option value="Manuel">Manuel</option>
                            <option value="Otomatik">Otomatik</option>
                        </select>

                        <input className="h-9 w-full px-2 rounded-lg border border-gray-300 bg-white"
                               placeholder="Renk" name="color" value={q.color} onChange={onChange}/>

                        <div className="flex gap-2">
                            <input className="h-9 w-full px-2 rounded-lg border border-gray-300 bg-white"
                                   type="number" placeholder="Min Yıl" name="minYear" value={q.minYear} onChange={onChange}/>
                            <input className="h-9 w-full px-2 rounded-lg border border-gray-300 bg-white"
                                   type="number" placeholder="Max Yıl" name="maxYear" value={q.maxYear} onChange={onChange}/>
                        </div>

                        <div className="flex gap-2">
                            <input className="h-9 w-full px-2 rounded-lg border border-gray-300 bg-white"
                                   type="number" placeholder="Min Km" name="minKm" value={q.minKm} onChange={onChange}/>
                            <input className="h-9 w-full px-2 rounded-lg border border-gray-300 bg-white"
                                   type="number" placeholder="Max Km" name="maxKm" value={q.maxKm} onChange={onChange}/>
                        </div>

                        <div className="flex gap-2">
                            <input className="h-9 w-full px-2 rounded-lg border border-gray-300 bg-white"
                                   type="number" placeholder="Min Fiyat" name="minPrice" value={q.minPrice} onChange={onChange}/>
                            <input className="h-9 w-full px-2 rounded-lg border border-gray-300 bg-white"
                                   type="number" placeholder="Max Fiyat" name="maxPrice" value={q.maxPrice} onChange={onChange}/>
                        </div>

                        <select className="h-9 w-full px-2 rounded-lg border border-gray-300 bg-white"
                                name="hasAccidentRecord" value={q.hasAccidentRecord} onChange={onChange}>
                            <option value="any">Hasar Kaydı (hepsi)</option>
                            <option value="yes">Sadece Var</option>
                            <option value="no">Sadece Yok</option>
                        </select>

                        <label className="flex items-center gap-2 text-sm">
                            <input type="checkbox" name="onlyWithImage" checked={q.onlyWithImage} onChange={onChange}/>
                            Sadece görseli olanlar
                        </label>

                        <button
                            type="button"
                            onClick={resetFilters}
                            className="h-9 w-full rounded-xl bg-gray-200 hover:bg-gray-300"
                        >
                            Sıfırla
                        </button>
                    </div>
                </Card>

                {/* SAĞ: Tablo (kompakt) */}
                <Card className="flex-1 md:overflow-visible overflow-x-auto">
                    <table className="w-full text-[13px]">
                        <thead className="bg-gray-50 text-gray-700">
                        <tr>
                            <th className="p-2 text-left w-[96px]">Görsel</th>
                            <th className="p-2 text-left">
                                <button className="font-semibold hover:underline" onClick={() => toggleSort("brand")}>
                                    Marka/Model {caret("brand")}
                                </button>
                            </th>
                            <th className="p-2 text-left">
                                <button className="font-semibold hover:underline" onClick={() => toggleSort("series")}>
                                    Seri {caret("series")}
                                </button>
                            </th>
                            <th className="p-2 text-left">
                                <button className="font-semibold hover:underline" onClick={() => toggleSort("productionyear")}>
                                    Yıl {caret("productionyear")}
                                </button>
                            </th>
                            <th className="p-2 text-left">
                                <button className="font-semibold hover:underline" onClick={() => toggleSort("mileage")}>
                                    Km {caret("mileage")}
                                </button>
                            </th>
                            <th className="p-2 text-left">
                                <button className="font-semibold hover:underline" onClick={() => toggleSort("fueltype")}>
                                    Yakıt {caret("fueltype")}
                                </button>
                            </th>
                            <th className="p-2 text-left">
                                <button className="font-semibold hover:underline" onClick={() => toggleSort("transmissiontype")}>
                                    Vites {caret("transmissiontype")}
                                </button>
                            </th>
                            <th className="p-2 text-left">
                                <button className="font-semibold hover:underline" onClick={() => toggleSort("color")}>
                                    Renk {caret("color")}
                                </button>
                            </th>
                            <th className="p-2 text-left">Hasar</th>
                            {/* Fiyat küçük ekranlarda gizli, lg ve üzeri göster */}
                            <th className="p-2 text-left hidden lg:table-cell">
                                <button className="font-semibold hover:underline" onClick={() => toggleSort("price")}>
                                    Fiyat {caret("price")}
                                </button>
                            </th>
                        </tr>
                        </thead>

                        <tbody>
                        {data.map((p) => (
                            <tr key={p.productid || p.id} className="border-t border-gray-100 hover:bg-gray-50">
                                <td className="p-2">
                                    <div className="w-[96px] h-[64px] bg-gray-100 rounded overflow-hidden flex items-center justify-center">
                                         {p.imageUrl ? (
                                           <img
                                             src={
                                               p.imageUrl.startsWith("/files/")
                                                 ? `${import.meta.env.VITE_API_BASE_URL || "http://localhost:8080"}${p.imageUrl}`
                                                 : p.imageUrl
                                             }
                                             alt={`${p.brand} ${p.model}`}
                                             className="w-full h-full object-cover"
                                             loading="lazy"
                                           />
                                         ) : (
                                            <span className="text-[11px] text-gray-500">Görsel yok</span>
                                        )}
                                    </div>
                                </td>
                                <td className="p-2 align-top">
                                    <div className="font-semibold">{p.brand} {p.model}</div>
                                </td>
                                <td className="p-2 align-top">{p.series || "—"}</td>
                                <td className="p-2 align-top">{p.productionyear ?? "—"}</td>
                                <td className="p-2 align-top">{fmtNum(p.mileage)}</td>
                                <td className="p-2 align-top">{p.fueltype || "—"}</td>
                                <td className="p-2 align-top">{p.transmissiontype || "—"}</td>
                                <td className="p-2 align-top">{p.color || "—"}</td>
                                <td className="p-2 align-top">
                                    {p.hasaccidentrecord === true ? (
                                        <span className="inline-block px-2 py-0.5 rounded bg-red-50 text-red-700 text-[11px]">Var</span>
                                    ) : p.hasaccidentrecord === false ? (
                                        <span className="inline-block px-2 py-0.5 rounded bg-green-50 text-green-700 text-[11px]">Yok</span>
                                    ) : "—"}
                                </td>
                                <td className="p-2 align-top font-semibold hidden lg:table-cell">
                                    {fmtNum(p.price)} ₺
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </Card>
            </div>
        </div>
    );
}
