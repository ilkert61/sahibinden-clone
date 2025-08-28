import { useState } from "react";
import { registerUser } from "../services/users";
import Card from "../components/ui/Card";
import Button from "../components/ui/Button";
import Input from "../components/ui/Input";

export default function Register() {
    const [form, setForm] = useState({
        firstName:"", lastName:"", email:"", phone:"",
        username:"", password:"", gender:"male", birthday:"2000-01-01"
    });
    const [msg, setMsg] = useState(null);
    const onChange = (e)=>setForm({...form,[e.target.name]:e.target.value});

    const handleSubmit = async (e)=>{
        e.preventDefault(); setMsg(null);
        try {
            await registerUser(form);
            setMsg("Kayıt başarılı! Artık giriş yapabilirsin.");
        } catch (err) {
            setMsg(err?.response?.data || "Kayıt sırasında hata oluştu");
        }
    };

    return (
        <div className="max-w-lg mx-auto p-4">
            <Card className="p-6">
                <h2 className="text-xl font-bold mb-1">Kayıt Ol</h2>
                <p className="text-sm text-gray-500 mb-4">Bilgilerini doldur.</p>
                <form onSubmit={handleSubmit} className="grid grid-cols-1 sm:grid-cols-2 gap-3">
                    <Input name="firstName" placeholder="Ad" value={form.firstName} onChange={onChange}/>
                    <Input name="lastName"  placeholder="Soyad" value={form.lastName} onChange={onChange}/>
                    <Input name="email"     placeholder="E-posta" value={form.email} onChange={onChange} className="sm:col-span-2"/>
                    <Input name="phone"     placeholder="Telefon" value={form.phone} onChange={onChange} className="sm:col-span-2"/>
                    <Input name="username"  placeholder="Kullanıcı adı" value={form.username} onChange={onChange}/>
                    <Input type="password" name="password" placeholder="Şifre" value={form.password} onChange={onChange}/>
                    <select name="gender" value={form.gender} onChange={onChange}
                            className="h-10 px-3 rounded-lg border border-gray-300 bg-white">
                        <option value="male">Erkek</option>
                        <option value="female">Kadın</option>
                    </select>
                    <Input type="date" name="birthday" value={form.birthday} onChange={onChange}/>
                    <Button type="submit" className="sm:col-span-2">Kayıt Ol</Button>
                </form>
                {msg && <p className="text-red-600 mt-3">{msg}</p>}
            </Card>
        </div>
    );
}
