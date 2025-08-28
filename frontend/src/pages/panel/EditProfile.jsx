import { useState } from "react";
import api from "../../lib/api";
import { useAuth } from "../../lib/AuthContext";

export default function EditProfile() {
    const { user } = useAuth();
    const [email, setEmail] = useState("");
    const [phone, setPhone] = useState("");
    const [msg, setMsg] = useState(null);

    const submit = async (e) => {
        e.preventDefault();
        setMsg(null);

        try {
            await api.put(`/users/username/${user.username}/contact`, {
                email: email || null,
                phone: phone || null,
            });
            setMsg("✅ Profil güncellendi.");
            setEmail("");
            setPhone("");
        } catch (err) {
            setMsg("❌ " + (err?.response?.data || "Güncellenemedi."));
        }
    };

    return (
        <div className="max-w-md mx-auto bg-white p-6 rounded-xl shadow-md">
            <h2 className="text-2xl font-bold mb-4 text-gray-800">
                Profil (Email / Telefon)
            </h2>
            <p className="mb-6 text-gray-600">
                Kullanıcı: <span className="font-semibold">{user.username}</span>
            </p>

            <form onSubmit={submit} className="space-y-4">
                <div>
                    <label className="block text-sm font-medium text-gray-700">
                        Yeni e-posta
                    </label>
                    <input
                        type="email"
                        value={email}
                        placeholder="E-posta gir"
                        onChange={(e) => setEmail(e.target.value)}
                        className="mt-1 w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring focus:ring-blue-200 p-2"
                    />
                </div>

                <div>
                    <label className="block text-sm font-medium text-gray-700">
                        Yeni telefon
                    </label>
                    <input
                        type="tel"
                        value={phone}
                        placeholder="Telefon gir"
                        onChange={(e) => setPhone(e.target.value)}
                        className="mt-1 w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring focus:ring-blue-200 p-2"
                    />
                </div>

                <button
                    type="submit"
                    className="w-full py-2 bg-blue-600 text-white rounded-md shadow hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-blue-500"
                >
                    Kaydet
                </button>
            </form>

            {msg && <p className="mt-4 text-sm text-gray-700">{msg}</p>}
        </div>
    );
}
