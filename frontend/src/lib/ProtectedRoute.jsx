import { Navigate, useLocation } from "react-router-dom";
import { useAuth } from "./AuthContext";

// Sadece sarmalayıcı komponent: <ProtectedRoute><Sayfa/></ProtectedRoute>
export default function ProtectedRoute({ children }) {
    const { user } = useAuth() || {};
    const location = useLocation();
    if (!user) {
        return <Navigate to="/login" replace state={{ from: location }} />;
    }
    return children;
}
