// src/pages/panel/MyProducts.jsx
import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import api from "../../lib/api";
import Card from "../../components/ui/Card";
import { useAuth } from "../../lib/AuthContext";

export default function MyProducts() {
    const { user } = useAuth();
    const [items, setItems] = useState([]);

    useEffect(() => {
        if (!user?.username) return;
        (async () => {
            try {
                const { data } = await api.get(`/product/my`, {
                    params: { username: user.username }
                });
                setItems(data.content || []);
            } catch (e) {
                console.error(e);
            }
        })();
    }, [user?.username]);

    return (
        <div className="max-w-6xl mx-auto p-4">
            <h2 className="text-xl font-bold mb-3">İlanlarım ({items.length})</h2>

            <Card className="overflow-x-auto">
                <table className="min-w-full text-sm">
                    <thead className="bg-gray-50 text-gray-700">
                    <tr>
                        <th className="p-3 text-left">#</th>
                        <th className="p-3 text-left">Marka/Model</th>
                        <th className="p-3 text-left">Seri</th>
                        <th className="p-3 text-left">Yıl</th>
                        <th className="p-3 text-left">Km</th>
                        <th className="p-3 text-left">Fiyat</th>
                        <th className="p-3 text-left">Durum</th>
                        <th className="p-3 text-left">İşlem</th>
                    </tr>
                    </thead>
                    <tbody>
                    {items.map((p, i) => (
                        <tr key={p.productid || p.id} className="border-t border-gray-100">
                            <td className="p-3">{i + 1}</td>
                            <td className="p-3 font-medium">{p.brand} {p.model}</td>
                            <td className="p-3">{p.series}</td>
                            <td className="p-3">{p.productionyear}</td>
                            <td className="p-3">{(p.mileage ?? 0).toLocaleString("tr-TR")}</td>
                            <td className="p-3">{(p.price ?? 0).toLocaleString("tr-TR")} ₺</td>
                            <td className="p-3">{p.status || "ACTIVE"}</td>
                            <td className="p-3">
                                <Link
                                    to={`/panel/product/${p.productid}/edit`}
                                    className="text-blue-600 hover:underline"
                                >
                                    Düzenle
                                </Link>
                            </td>
                        </tr>
                    ))}
                    {items.length === 0 && (
                        <tr><td className="p-4 text-gray-500" colSpan={8}>Henüz ilan yok.</td></tr>
                    )}
                    </tbody>
                </table>
            </Card>
        </div>
    );
}
