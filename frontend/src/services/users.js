import api from "../lib/api";

export async function registerUser(payload) {
    // Debug
    console.log("registerUser() payload:", payload);
    const { data } = await api.post("/users/addUsers", payload);
    console.log("registerUser() response:", data);
    return data;
}
