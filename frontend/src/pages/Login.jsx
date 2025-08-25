import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../services/auth";

export default function Login() {
    const [user, setUser] = useState("");
    const [pass, setPass] = useState("");
    const [msg, setMsg] = useState(null);
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMsg(null);
        try {
            const res = await login({ user, pass }); // { success, message, userId }
            if (!res.success) {
                setMsg(res.message || "Giriş başarısız");
                return;
            }
            localStorage.setItem("userId", res.userId);
            navigate("/");
        } catch (err) {
            setMsg(err?.response?.data?.message || "Sunucu hatası");
        }
    };

    return (
        <div style={{ padding: 24, maxWidth: 420 }}>
            <h2>Giriş Yap</h2>
            <form onSubmit={handleSubmit}>
                <input
                    placeholder="Kullanıcı / E-posta"
                    value={user}
                    onChange={(e) => setUser(e.target.value)}
                    style={{ width: "100%", padding: 8, marginBottom: 8 }}
                />
                <input
                    type="password"
                    placeholder="Şifre"
                    value={pass}
                    onChange={(e) => setPass(e.target.value)}
                    style={{ width: "100%", padding: 8, marginBottom: 8 }}
                />
                <button type="submit">Giriş</button>
            </form>
            {msg && <p style={{ color: "crimson", marginTop: 8 }}>{msg}</p>}
        </div>
    );
}
