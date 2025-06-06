import api from './api'; // instância do Axios já configurada

const listarTipos = () => api.get('/tipos'); // <- endpoint que você vai criar no backend
const criarTipo = (dados) => api.post('/tipos', dados);
const atualizarTipo = (id, dados) => api.put(`/tipos/${id}`, dados);
const deletarTipo = (id) => api.delete(`/tipos/${id}`);

export {
  listarTipos,
  criarTipo,
  atualizarTipo,
  deletarTipo
};
