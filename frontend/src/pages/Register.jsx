import { useState } from "react";
import { registerUser } from "../services/users";

export default function Register() {
    const [form, setForm] = useState({
        firstName: "", lastName: "", email: "", phone: "",
        username: "", password: "", gender: "male", birthday: "2000-01-01",
    });
    const [msg, setMsg] = useState(null);

    const onChange = (e) => setForm({ ...form, [e.target.name]: e.target.value });

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMsg(null);

        try {
            console.log("Submit form:", form); // DEBUG
            const res = await registerUser(form);
            console.log("Submit response:", res); // DEBUG
            setMsg("Kayıt başarılı! Artık giriş yapabilirsin.");
        } catch (err) {
            console.error("Submit error:", err); // DEBUG
            setMsg(err?.response?.data || "Kayıt sırasında hata oluştu");
        }
    };

    return (
        <div style={{ padding: 24, maxWidth: 520 }}>
            <h2>Kayıt Ol</h2>
            <form onSubmit={handleSubmit}>
                <input name="firstName" placeholder="Ad" value={form.firstName} onChange={onChange} /><br/>
                <input name="lastName"  placeholder="Soyad" value={form.lastName} onChange={onChange} /><br/>
                <input name="email"     placeholder="E-posta" value={form.email} onChange={onChange} /><br/>
                <input name="phone"     placeholder="Telefon" value={form.phone} onChange={onChange} /><br/>
                <input name="username"  placeholder="Kullanıcı adı" value={form.username} onChange={onChange} /><br/>
                <input type="password" name="password" placeholder="Şifre" value={form.password} onChange={onChange} /><br/>
                <select name="gender" value={form.gender} onChange={onChange}>
                    <option value="male">male</option>
                    <option value="female">female</option>
                </select><br/>
                <input type="date" name="birthday" value={form.birthday} onChange={onChange} /><br/><br/>
                <button type="submit">Kayıt Ol</button>
            </form>
            {msg && <p style={{ marginTop: 8, color: msg.includes("başarılı") ? "green" : "crimson" }}>{msg}</p>}
        </div>
    );
}
