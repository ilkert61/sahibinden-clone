import api from "../lib/api";

export async function fetchProducts({ page = 0, size = 12, sort = "productionyear,desc" } = {}) {
    const { data } = await api.get("/product/list", { params: { page, size, sort } });
    return data;
}
