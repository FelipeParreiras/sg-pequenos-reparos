import { useState } from 'react';
import { aceitarServico } from '../../services/servicoService'; // Corrigido o caminho
import { getUserProfile } from '../../services/authService';   // Corrigido o caminho

const ModalAgendarServico = ({ servico, onClose, onServicoAtualizado }) => {
  const [data, setData] = useState('');
  const [horario, setHorario] = useState('');
  const [loading, setLoading] = useState(false);

  const handleAgendar = async () => {
    if (!data || !horario) {
      alert('Por favor, preencha a data e o horário.');
      return;
    }

    setLoading(true);
    try {
      const admin = await getUserProfile();
      await aceitarServico(servico.id, admin.id, data, horario);
      alert('Serviço agendado com sucesso!');
      onServicoAtualizado(); // Atualiza a lista
      onClose(); // Fecha a modal
    } catch (error) {
      console.error('Erro ao agendar serviço:', error);
      alert('Erro ao agendar serviço.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <h2>Agendar Serviço</h2>
        <p><strong>Serviço:</strong> {servico.nome}</p>
        <p><strong>Cliente:</strong> {servico.clienteNome}</p> {/* Corrigido! */}

        <label>Data:</label>
        <input
          type="date"
          value={data}
          onChange={(e) => setData(e.target.value)}
        />

        <label>Horário:</label>
        <input
          type="time"
          value={horario}
          onChange={(e) => setHorario(e.target.value)}
        />

        <div className="modal-buttons">
          <button onClick={handleAgendar} disabled={loading}>
            {loading ? 'Agendando...' : 'Agendar'}
          </button>
          <button onClick={onClose} style={{ marginLeft: '10px' }}>
            Cancelar
          </button>
        </div>
      </div>
    </div>
  );
};

export default ModalAgendarServico;
