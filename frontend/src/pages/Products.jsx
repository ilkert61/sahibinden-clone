import { useEffect, useState } from "react";
import { fetchProducts } from "../services/products";

export default function Products() {
    const [items, setItems] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        (async () => {
            try {
                const page = await fetchProducts(); // Spring Page<Products>
                setItems(page.content || []);
            } finally {
                setLoading(false);
            }
        })();
    }, []);

    if (loading) return <div style={{ padding: 24 }}>Yükleniyor...</div>;

    return (
        <div style={{ padding: 24 }}>
            <h2>Ürünler</h2>
            {items.length === 0 && <p>Kayıt yok.</p>}
            <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fill,minmax(220px,1fr))", gap: 12 }}>
                {items.map((p) => (
                    <div key={p.productid || p.id} style={{ border: "1px solid #eee", borderRadius: 8, padding: 12 }}>
                        <b>{p.brand} {p.model} {p.series}</b>
                        <div>Yıl: {p.productionyear} • Km: {p.mileage}</div>
                        <small>Yakıt: {p.fueltype} • Vites: {p.transmissiontype}</small>
                    </div>
                ))}
            </div>
        </div>
    );
}
