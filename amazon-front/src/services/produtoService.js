import axios from "axios";
const baseURL = 'http://localhost:8080/produtos'

export async function getAllProdutos() {
  return axios.get(baseURL)
}
