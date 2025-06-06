import { useState, useEffect } from 'react';
import { listarServicos } from '../services/servicoService'; // ⬅️ usamos o service, não axios direto

const HistoricoServicosPage = () => {
  const [servicosConcluidos, setServicosConcluidos] = useState([]);

  useEffect(() => {
    fetchServicosConcluidos();
  }, []);

  const fetchServicosConcluidos = async () => {
    try {
      const response = await listarServicos();
      const concluidos = response.data.filter(servico => servico.status === 'CONCLUIDO');
      setServicosConcluidos(concluidos);
    } catch (error) {
      console.error('Erro ao buscar serviços concluídos:', error);
    }
  };

  return (
    <div className="historico-page">
      <h2>Histórico de Serviços Concluídos</h2>
      {servicosConcluidos.length === 0 ? (
        <p>Você ainda não concluiu nenhum serviço.</p>
      ) : (
        <ul className="historico-lista">
          {servicosConcluidos.map(servico => (
            <li key={servico.id} className="historico-card">
              <h4>{servico.nome}</h4>
              <p><strong>Tipo:</strong> {servico.tipoServico.nome}</p>
              <p><strong>Data:</strong> {servico.data ? new Date(servico.data).toLocaleDateString() : 'Não agendado'}</p>
              {/* 🚧 Futuro: botão de Avaliação vai ficar aqui */}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default HistoricoServicosPage;
