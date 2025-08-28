// src/pages/panel/Panel.jsx
import { Link } from "react-router-dom";
import Card from "../../components/ui/Card";

export default function Panel() {
    return (
        <div className="max-w-6xl mx-auto p-4">
            <h2 className="text-xl font-bold mb-4">Kullanıcı Paneli</h2>

            <div className="grid sm:grid-cols-3 gap-4">
                <Card className="p-5 hover:shadow-md transition">
                    <h3 className="font-semibold mb-2">İlan Ekle</h3>
                    <Link to="/panel/add" className="text-blue-600 hover:underline">Yeni ilan oluştur →</Link>
                </Card>
                <Card className="p-5 hover:shadow-md transition">
                    <h3 className="font-semibold mb-2">İlanlarım</h3>
                    <Link to="/panel/products" className="text-blue-600 hover:underline">Tüm ilanlarım →</Link>
                </Card>
                <Card className="p-5 hover:shadow-md transition">
                    <h3 className="font-semibold mb-2">Profil</h3>
                    <Link to="/panel/profile" className="text-blue-600 hover:underline">E-posta/Telefon düzenle →</Link>
                </Card>
            </div>
        </div>
    );
}
