import api from "../lib/api";

export async function login({ user, pass }) {
    const { data } = await api.post("/auth/login", { user, pass });
    return data; // { success, message, userId }
}
