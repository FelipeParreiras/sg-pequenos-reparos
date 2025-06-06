import api from './api'; // sua instância já configurada com baseURL

// Pega o itinerário atual do usuário/admin
const getItinerario = () => api.get('/itinerario'); 

// Salva ou atualiza o itinerário
const saveItinerario = (dados) => api.post('/itinerario', dados); 

export {
  getItinerario,
  saveItinerario
};
