import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Products from "./pages/Products";

export default function App() {
    return (
        <BrowserRouter>
            <nav style={{ display: "flex", gap: 12, padding: 12 }}>
                <Link to="/">Ana Sayfa</Link>
                <Link to="/products">Ürünler</Link>
                <Link to="/login">Giriş</Link>
                <Link to="/register">Kayıt Ol</Link>
            </nav>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
                <Route path="/products" element={<Products />} />
            </Routes>
        </BrowserRouter>
    );
}
