import { createContext, useContext, useEffect, useState } from "react";

const AuthCtx = createContext(null);
export const useAuth = () => useContext(AuthCtx);

export default function AuthProvider({ children }) {
    const [user, setUser] = useState(() => {
        try { return JSON.parse(localStorage.getItem("auth")) || null; } catch { return null; }
    });

    useEffect(() => {
        if (user) localStorage.setItem("auth", JSON.stringify(user));
        else localStorage.removeItem("auth");
    }, [user]);

    const login = (payload) => setUser(payload);       // { userId, username }
    const logout = () => setUser(null);

    return <AuthCtx.Provider value={{ user, login, logout }}>{children}</AuthCtx.Provider>;
}
