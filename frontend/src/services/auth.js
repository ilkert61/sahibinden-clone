import axios from "axios";

export const login = async (payload) => {
    const res = await axios.post("http://localhost:8080/auth/login", payload);
    return res.data; }
