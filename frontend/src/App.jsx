// src/App.jsx
import { BrowserRouter, Routes, Route, Link, useNavigate } from "react-router-dom";

import AuthProvider, { useAuth } from "./lib/AuthContext";
import ProtectedRoute from "./lib/ProtectedRoute";

import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Products from "./pages/Products";

import Panel from "./pages/panel/Panel";
import AddProduct from "./pages/panel/AddProduct";
import EditProduct from "./pages/panel/EditProduct";
import EditProfile from "./pages/panel/EditProfile";
import MyProducts from "./pages/panel/MyProducts";

import Button from "./components/ui/Button";

function NavBar() {
    const navigate = useNavigate();
    const { user, logout } = useAuth() || {};

    const handleLogout = () => {
        if (logout) logout(); else localStorage.removeItem("auth");
        navigate("/");
    };

    return (
        <header className="bg-white border-b border-gray-200">
            <div className="max-w-6xl mx-auto px-4 h-14 flex items-center justify-between">
                <Link to="/" className="flex items-center gap-2 font-extrabold text-lg">
                    <span className="w-3 h-6 bg-brand rounded-sm inline-block" />
                    <span>Sahibinden<span className="text-gray-500">.clone</span></span>
                </Link>

                <nav className="flex items-center gap-3">
                    <Link to="/products" className="hover:underline">Ürünler</Link>
                    {user ? (
                        <>
                            <Link to="/panel" className="hover:underline">Panel</Link>
                            <Link to="/panel/products" className="hover:underline">İlanlarım</Link>
                            <Button onClick={handleLogout} className="h-9">Çıkış</Button>
                        </>
                    ) : (
                        <>
                            <Link to="/login"><Button className="h-9">Giriş</Button></Link>
                            <Link to="/register"><Button className="h-9">Kayıt Ol</Button></Link>
                        </>
                    )}
                </nav>
            </div>
        </header>
    );
}

export default function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <NavBar />
                <div className="max-w-6xl mx-auto p-4">
                    <Routes>
                        {/* public */}
                        <Route path="/" element={<Home />} />
                        <Route path="/products" element={<Products />} />
                        <Route path="/login" element={<Login />} />
                        <Route path="/register" element={<Register />} />

                        {/* protected */}
                        <Route
                            path="/panel"
                            element={
                                <ProtectedRoute>
                                    <Panel />
                                </ProtectedRoute>
                            }
                        />
                        <Route
                            path="/panel/add"
                            element={
                                <ProtectedRoute>
                                    <AddProduct />
                                </ProtectedRoute>
                            }
                        />
                        <Route
                            path="/panel/products"
                            element={
                                <ProtectedRoute>
                                    <MyProducts />
                                </ProtectedRoute>
                            }
                        />
                        <Route
                            path="/panel/product/:productid/edit"
                            element={
                                <ProtectedRoute>
                                    <EditProduct />
                                </ProtectedRoute>
                            }
                        />
                        <Route
                            path="/panel/profile"
                            element={
                                <ProtectedRoute>
                                    <EditProfile />
                                </ProtectedRoute>
                            }
                        />
                    </Routes>
                </div>
            </BrowserRouter>
        </AuthProvider>
    );
}
