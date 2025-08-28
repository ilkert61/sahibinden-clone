import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login as apiLogin } from "../services/auth";
import { useAuth } from "../lib/AuthContext";

export default function Login() {
    const [user, setUser] = useState("");
    const [pass, setPass] = useState("");
    const [msg, setMsg] = useState(null);
    const navigate = useNavigate();
    const { login } = useAuth();

    const handleSubmit = async (e) => {
        e.preventDefault(); setMsg(null);
        try {
            const res = await apiLogin({ user, pass });
            if (!res.success) return setMsg(res.message || "Giriş başarısız");
            // Context + localStorage senkronu
            login({ userId: res.userId, username: res.username });
            navigate("/panel");
        } catch (err) {
            setMsg(err?.response?.data || "Sunucu hatası");
        }
    };

    return (
        <div className="max-w-md mx-auto p-4">
            <h2 className="text-xl font-bold mb-1">Giriş Yap</h2>
            <p className="text-sm text-gray-500 mb-4">Hesabınla oturum aç.</p>
            <form onSubmit={handleSubmit} className="flex items-center gap-2">
                <input
                    className="h-10 px-3 rounded-lg border border-gray-300 bg-white"
                    placeholder="Kullanıcı / E-posta"
                    value={user} onChange={e=>setUser(e.target.value)}
                />
                <input
                    className="h-10 px-3 rounded-lg border border-gray-300 bg-white"
                    type="password" placeholder="Şifre"
                    value={pass} onChange={e=>setPass(e.target.value)}
                />
                <button className="px-4 h-10 rounded-xl bg-brand hover:bg-brandDark font-semibold">Giriş</button>
            </form>
            {msg && <p className="text-red-600 mt-3">{msg}</p>}
        </div>
    );
}
