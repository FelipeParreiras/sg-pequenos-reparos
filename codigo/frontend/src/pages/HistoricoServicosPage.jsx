import { useState, useEffect } from 'react';
import { listarServicos } from '../services/servicoService';

const HistoricoServicosPage = () => {
  const [servicos, setServicos] = useState([]);
  const [filtroStatus, setFiltroStatus] = useState('');
  const [filtroTipo, setFiltroTipo] = useState('');
  const [filtroNome, setFiltroNome] = useState('');
  const [filtroData, setFiltroData] = useState('');

  useEffect(() => {
    fetchServicos();
  }, []);

  const fetchServicos = async () => {
    try {
      const response = await listarServicos();
      setServicos(response.data);
    } catch (error) {
      console.error('Erro ao buscar serviços:', error);
    }
  };

  const servicosFiltrados = servicos.filter(servico => {
    const nomeMatch = servico.nome.toLowerCase().includes(filtroNome.toLowerCase());
    const statusMatch = filtroStatus ? servico.status === filtroStatus : true;
    const tipoMatch = filtroTipo ? (
      typeof servico.tipoServico === 'string'
        ? servico.tipoServico === filtroTipo
        : servico.tipoServico?.nome === filtroTipo
    ) : true;
    const dataMatch = filtroData ? (servico.data?.includes?.(filtroData) || new Date(servico.data).toLocaleDateString().includes(filtroData)) : true;
    return nomeMatch && statusMatch && tipoMatch && dataMatch;
  });

  // Coleta os tipos únicos
  const tiposUnicos = Array.from(new Set(servicos.map(s =>
    typeof s.tipoServico === 'string' ? s.tipoServico : s.tipoServico?.nome
  )));

  return (
    <div className="historico-page" style={{ padding: '20px' }}>
      <h2>Histórico de Serviços</h2>

      {/* Filtros */}
      <div style={{ marginBottom: '20px', display: 'flex', flexWrap: 'wrap', gap: '10px' }}>
        <input
          type="text"
          placeholder="Buscar por nome..."
          value={filtroNome}
          onChange={(e) => setFiltroNome(e.target.value)}
        />
        <input
          type="text"
          placeholder="Filtrar por data (dd/mm/aaaa)"
          value={filtroData}
          onChange={(e) => setFiltroData(e.target.value)}
        />
        <select value={filtroStatus} onChange={(e) => setFiltroStatus(e.target.value)}>
          <option value="">Todos os Status</option>
          <option value="SOLICITADO">Solicitado</option>
          <option value="ACEITO">Agendado</option>
          <option value="CONCLUIDO">Concluído</option>
          <option value="RECUSADO">Recusado</option>
          <option value="CANCELADO">Cancelado</option>
        </select>
        <select value={filtroTipo} onChange={(e) => setFiltroTipo(e.target.value)}>
          <option value="">Todos os Tipos</option>
          {tiposUnicos.map(tipo => (
            <option key={tipo} value={tipo}>{tipo}</option>
          ))}
        </select>
      </div>

      {/* Lista Filtrada */}
      {servicosFiltrados.length === 0 ? (
        <p>Nenhum serviço encontrado com os filtros aplicados.</p>
      ) : (
        <ul className="historico-lista" style={{ listStyle: 'none', padding: 0 }}>
          {servicosFiltrados.map(servico => (
            <li key={servico.id} className="historico-card" style={{ border: '1px solid #ddd', padding: '10px', marginBottom: '10px' }}>
              <h4>{servico.nome}</h4>
              <p><strong>Tipo:</strong> {typeof servico.tipoServico === 'string' ? servico.tipoServico : servico.tipoServico?.nome}</p>
              <p><strong>Status:</strong> {servico.status}</p>
              <p><strong>Data:</strong> {servico.data ? new Date(servico.data).toLocaleDateString() : 'Não agendado'}</p>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default HistoricoServicosPage;
