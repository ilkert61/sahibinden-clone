// src/services/products.js
import api from "../lib/api";

export async function fetchProducts(page = 0, size = 20) {
    const res = await api.get(`/product/list?page=${page}&size=${size}`);
    return Array.isArray(res.data) ? res.data : (res.data?.content || []);
}
export async function getProductById(productid) {
    const res = await api.get(`/product/${productid}`);
    return res.data;
}
export async function updateProduct(productid, payload, username) {
    return api.put(`/product/editproduct/${productid}`, payload, { params: { username }});
}
export async function deleteProduct(productid, username) {
    return api.delete(`/product/delete/${productid}`, { params: { username }});
}

// NEW: image upload / delete
export async function uploadProductImage(productid, file) {
    const form = new FormData();
    form.append("file", file);
    const res = await api.post(`/product/image/${productid}`, form, {
        headers: { "Content-Type": "multipart/form-data" },
    });
    return res.data; // updated product
}
export async function deleteProductImage(productid) {
    const res = await api.delete(`/product/image/${productid}`);
    return res.data; // updated product
}
