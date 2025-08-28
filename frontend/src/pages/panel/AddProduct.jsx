import { useState } from "react";
import api from "../../lib/api";
import { useAuth } from "../../lib/AuthContext";
import { uploadProductImage } from "../../services/products";

export default function AddProduct() {
    const { user } = useAuth();
    const [f, setF] = useState({
        brand: "",
        model: "",
        series: "",
        productionYear: 2015,
        fuelType: "Benzin",
        engineVolume: 1600,
        transmissionType: "Manuel",
        mileage: 120000,
        color: "Beyaz",
        hasAccidentRecord: false,
        price: 0,
    });

    const [file, setFile] = useState(null);
    const [preview, setPreview] = useState(null);
    const [msg, setMsg] = useState(null);

    const onChange = (e) => {
        const { name, value, type, checked, files } = e.target;
        if (type === "file") {
            const f = files?.[0] || null;
            setFile(f);
            setPreview(f ? URL.createObjectURL(f) : null);
            return;
        }
        setF((prev) => ({
            ...prev,
            [name]: type === "checkbox" ? checked : value,
        }));
    };

    const submit = async (e) => {
        e.preventDefault();
        setMsg(null);

        try {
            const payload = { ...f, ownerUsername: user?.username || "anon" };
            const { data: created } = await api.post("/product/addProduct", payload);

            // UUID veya Long olabilir — backend'e göre kontrol et
            const pid = created.productid || created.id;
            if (file && pid) {
                await uploadProductImage(pid, file);
            }

            setMsg("✅ İlan eklendi! ID: " + pid);
            setFile(null);
            setPreview(null);
        } catch (err) {
            setMsg("❌ " + (err?.response?.data || "İlan eklenemedi"));
        }
    };

    return (
        <div className="max-w-3xl mx-auto p-6 bg-white rounded-xl shadow-md">
            <h3 className="text-2xl font-bold mb-6 text-gray-800">İlan Ekle</h3>

            <form
                onSubmit={submit}
                className="grid grid-cols-1 sm:grid-cols-2 gap-4"
            >
                {[
                    { name: "brand", label: "Marka" },
                    { name: "model", label: "Model" },
                    { name: "series", label: "Seri" },
                    { name: "productionYear", label: "Yıl", type: "number" },
                    { name: "fuelType", label: "Yakıt" },
                    { name: "engineVolume", label: "Motor Hacmi", type: "number" },
                    { name: "transmissionType", label: "Vites" },
                    { name: "mileage", label: "Km", type: "number" },
                    { name: "color", label: "Renk" },
                    { name: "price", label: "Fiyat (₺)", type: "number" },
                ].map(({ name, label, type = "text" }) => (
                    <div key={name}>
                        <label className="block text-sm font-medium text-gray-700">
                            {label}
                        </label>
                        <input
                            name={name}
                            type={type}
                            value={f[name]}
                            onChange={onChange}
                            className="mt-1 w-full rounded-md border-gray-300 shadow-sm
                         focus:border-blue-500 focus:ring focus:ring-blue-200 p-2"
                        />
                    </div>
                ))}

                {/* Hasar Kaydı */}
                <label className="flex items-center gap-2 sm:col-span-2 mt-2">
                    <input
                        type="checkbox"
                        name="hasAccidentRecord"
                        checked={f.hasAccidentRecord}
                        onChange={onChange}
                        className="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300 rounded"
                    />
                    Hasar Kaydı
                </label>

                {/* Görsel */}
                <div className="sm:col-span-2 mt-4">
                    <label className="block text-sm font-medium text-gray-700">
                        İlan Fotoğrafı
                    </label>
                    <input
                        type="file"
                        accept="image/*"
                        onChange={onChange}
                        className="mt-1 block w-full text-sm text-gray-500
                       file:mr-4 file:py-2 file:px-4
                       file:rounded-md file:border-0
                       file:text-sm file:font-semibold
                       file:bg-blue-50 file:text-blue-700
                       hover:file:bg-blue-100"
                    />
                    {preview && (
                        <img
                            src={preview}
                            alt="preview"
                            className="mt-2 w-64 h-40 object-cover rounded-lg border"
                        />
                    )}
                </div>

                {/* Submit */}
                <div className="col-span-2 flex justify-end mt-6">
                    <button
                        type="submit"
                        className="px-6 py-2 bg-blue-600 text-white rounded-md shadow hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                    >
                        Kaydet
                    </button>
                </div>
            </form>

            {msg && <p className="mt-4 text-sm text-gray-700">{msg}</p>}
        </div>
    );
}
