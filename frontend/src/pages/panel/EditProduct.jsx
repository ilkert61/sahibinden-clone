// src/pages/panel/EditProduct.jsx
import { useEffect, useMemo, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { getProductById, updateProduct, deleteProduct, uploadProductImage, deleteProductImage } from "../../services/products";
import { useAuth } from "../../lib/AuthContext";
import Card from "../../components/ui/Card";
import Input from "../../components/ui/Input";
import Button from "../../components/ui/Button";

export default function EditProduct() {
    const { productid: productidParam } = useParams();
    const productid = Number(productidParam);
    const { user } = useAuth() || {};
    const nav = useNavigate();

    const [original, setOriginal] = useState(null);
    const [f, setF] = useState({
        brand:"", model:"", series:"",
        productionYear:"", fuelType:"",
        engineVolume:"", transmissionType:"",
        mileage:"", color:"", hasAccidentRecord:false,
        price:""
    });
    const [msg, setMsg] = useState(null);
    const [loading, setLoading] = useState(true);
    const [saving, setSaving] = useState(false);

    // image local state
    const [file, setFile] = useState(null);
    const [preview, setPreview] = useState(null);

    useEffect(() => {
        (async () => {
            setLoading(true);
            try {
                const data = await getProductById(productid);
                setOriginal(data);
                setF({
                    brand: data.brand ?? "",
                    model: data.model ?? "",
                    series: data.series ?? "",
                    productionYear: data.productionyear ?? "",
                    fuelType: data.fueltype ?? "",
                    engineVolume: data.enginevolume ?? "",
                    transmissionType: data.transmissiontype ?? "",
                    mileage: data.mileage ?? "",
                    color: data.color ?? "",
                    hasAccidentRecord: !!data.hasaccidentrecord,
                    price: data.price ?? ""
                });
            } catch {
                setMsg("Ürün verisi yüklenemedi.");
            } finally {
                setLoading(false);
            }
        })();
    }, [productid]);

    const onChange = (e) => {
        const { name, value, type, checked, files } = e.target;
        if (type === "file") {
            const f = files?.[0] || null;
            setFile(f);
            setPreview(f ? URL.createObjectURL(f) : null);
            return;
        }
        setF(prev => ({ ...prev, [name]: type === "checkbox" ? checked : value }));
    };

    const payload = useMemo(() => {
        if (!original) return {};
        const cur = {
            brand: f.brand,
            model: f.model,
            series: f.series,
            productionYear: f.productionYear === "" ? null : Number(f.productionYear),
            fuelType: f.fuelType,
            engineVolume: f.engineVolume === "" ? null : Number(f.engineVolume),
            transmissionType: f.transmissionType,
            mileage: f.mileage === "" ? null : Number(f.mileage),
            color: f.color,
            hasAccidentRecord: !!f.hasAccidentRecord,
            price: f.price === "" ? null : Number(f.price),
        };
        const orig = {
            brand: original.brand,
            model: original.model,
            series: original.series,
            productionYear: original.productionyear,
            fuelType: original.fueltype,
            engineVolume: original.enginevolume,
            transmissionType: original.transmissiontype,
            mileage: original.mileage,
            color: original.color,
            hasAccidentRecord: original.hasaccidentrecord,
            price: original.price,
        };
        const out = {};
        Object.keys(cur).forEach(k => {
            const nv = cur[k] ?? null;
            const no = orig[k] ?? null;
            if (JSON.stringify(nv) !== JSON.stringify(no)) out[k] = nv;
        });
        return out;
    }, [f, original]);

    const submit = async (e) => {
        e.preventDefault(); setMsg(null);
        if (!original) return setMsg("Ürün verisi bulunamadı.");
        if (Object.keys(payload).length === 0 && !file) return setMsg("Değişiklik yapmadın.");

        try {
            setSaving(true);
            if (Object.keys(payload).length > 0) {
                await updateProduct(productid, payload, user?.username || "");
            }
            if (file) {
                await uploadProductImage(productid, file);
                setFile(null); setPreview(null);
            }
            setMsg("Güncellendi!");
            // görüntüyü/alanları yenile
            const fresh = await getProductById(productid);
            setOriginal(fresh);
        } catch (err) {
            setMsg(err?.response?.data || err?.message || "Güncellenemedi");
        } finally {
            setSaving(false);
        }
    };

    if (loading) {
        return <div className="max-w-3xl mx-auto p-4"><Card className="p-6">Yükleniyor…</Card></div>;
    }
    if (!original) {
        return (
            <div className="max-w-3xl mx-auto p-4">
                <Card className="p-6">
                    <p className="text-red-600 mb-3">Ürün verisi bulunamadı.</p>
                    <Button onClick={() => nav("/panel/products")}>İlanlara Dön</Button>
                </Card>
            </div>
        );
    }

    return (
        <div className="max-w-3xl mx-auto p-4">
            <h2 className="text-xl font-bold mb-4">İlan Düzenle</h2>
            <Card className="p-6">
                <form onSubmit={submit} className="grid grid-cols-1 sm:grid-cols-2 gap-3">
                    <Input name="brand" placeholder="Marka" value={f.brand} onChange={onChange}/>
                    <Input name="model" placeholder="Model" value={f.model} onChange={onChange}/>
                    <Input name="series" placeholder="Seri" value={f.series} onChange={onChange}/>
                    <Input type="number" name="productionYear" placeholder="Yıl" value={f.productionYear} onChange={onChange}/>
                    <Input name="fuelType" placeholder="Yakıt" value={f.fuelType} onChange={onChange}/>
                    <Input type="number" name="engineVolume" placeholder="Motor Hacmi" value={f.engineVolume} onChange={onChange}/>
                    <Input name="transmissionType" placeholder="Vites" value={f.transmissionType} onChange={onChange}/>
                    <Input type="number" name="mileage" placeholder="Km" value={f.mileage} onChange={onChange}/>
                    <Input name="color" placeholder="Renk" value={f.color} onChange={onChange}/>
                    <Input type="number" name="price" placeholder="Fiyat (₺)" value={f.price} onChange={onChange}/>
                    <label className="flex items-center gap-2 sm:col-span-2">
                        <input type="checkbox" name="hasAccidentRecord" checked={!!f.hasAccidentRecord} onChange={onChange}/>
                        Hasar Kaydı
                    </label>

                    {/* Görsel alanı */}
                    <div className="sm:col-span-2">
                        <div className="text-sm text-gray-700 mb-2">Mevcut Görsel</div>
                        <div className="flex items-center gap-4">
                            <div className="w-40 h-28 bg-gray-100 rounded overflow-hidden flex items-center justify-center">
                                {preview ? (
                                    <img src={preview} alt="preview" className="w-full h-full object-cover" />
                                     ) : original.imageUrl ? (
                                       <img
                                         src={
                                           original.imageUrl.startsWith("/files/")
                                             ? `${import.meta.env.VITE_API_BASE_URL || "http://localhost:8080"}${original.imageUrl}`
                                             : original.imageUrl
                                         }
                                         alt="current"
                                         className="w-full h-full object-cover"
                                       />
                                ) : (
                                    <span className="text-xs text-gray-500">Görsel yok</span>
                                )}
                            </div>
                            <div className="space-y-2">
                                <input type="file" accept="image/*" onChange={onChange}/>
                                {original.imageUrl && (
                                    <Button
                                        type="button"
                                        className="bg-red-600 hover:bg-red-700 text-white"
                                        onClick={async () => {
                                            try {
                                                await deleteProductImage(productid);
                                                const fresh = await getProductById(productid);
                                                setOriginal(fresh);
                                                setPreview(null); setFile(null);
                                            } catch (e) {
                                                alert("Görsel silinemedi");
                                            }
                                        }}
                                    >
                                        Görseli Kaldır
                                    </Button>
                                )}
                            </div>
                        </div>
                    </div>

                    <div className="sm:col-span-2 flex gap-2">
                        <Button type="submit" disabled={saving}>{saving ? "Kaydediliyor..." : "Kaydet"}</Button>
                        <Button type="button" className="bg-gray-200" onClick={() => nav("/panel/products")}>Vazgeç</Button>
                        <Button
                            type="button"
                            className="bg-red-600 hover:bg-red-700 text-white"
                            onClick={async () => {
                                if (!window.confirm("Bu ilanı silmek istediğine emin misin?")) return;
                                try {
                                    await deleteProduct(productid, user?.username || "");
                                    alert("İlan silindi!");
                                    nav("/panel/products");
                                } catch (err) {
                                    alert("Silme işlemi başarısız: " + (err.response?.data || err.message));
                                }
                            }}
                        >
                            Sil
                        </Button>
                    </div>
                </form>
                {msg && <p className="mt-3 text-sm">{msg}</p>}
            </Card>
        </div>
    );
}
