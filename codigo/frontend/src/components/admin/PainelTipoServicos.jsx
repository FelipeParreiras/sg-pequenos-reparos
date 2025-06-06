import React, { useEffect, useState } from 'react';
import { listarTipos } from '../../services/tipoService';
import EditTipoModal from '../EditTipoModal';

const PainelTipoServicos = () => {
  const [tipos, setTipos] = useState([]);
  const [busca, setBusca] = useState('');
  const [modalAberto, setModalAberto] = useState(false);
  const [tipoEditando, setTipoEditando] = useState(null);

  useEffect(() => {
    carregarTipos();
  }, []);

  const carregarTipos = async () => {
    try {
      const response = await listarTipos(); // Aqui temporário
      setTipos(response.data);
    } catch (error) {
      console.error('Erro ao listar tipos:', error);
    }
  };

  const handleBuscar = (e) => {
    setBusca(e.target.value);
  };

  const tiposFiltrados = tipos.filter(tipo =>
    tipo.nome.toLowerCase().includes(busca.toLowerCase())
  );

  const abrirModal = (tipo = null) => {
    setTipoEditando(tipo);
    setModalAberto(true);
  };

  const fecharModal = () => {
    setTipoEditando(null);
    setModalAberto(false);
    carregarTipos(); // Recarrega a lista após add/edit
  };

  return (
    <div className="painel-tipo-servicos-container">
      <div className="painel-tipo-servicos-header">
        <h2>Gerenciamento de Tipos de Serviço</h2>
        <button className="btn-adicionar" onClick={() => abrirModal()}>Adicionar Tipo</button>
      </div>

      <div className="painel-tipo-servicos-filtro">
        <input
          type="text"
          placeholder="Buscar por nome..."
          value={busca}
          onChange={handleBuscar}
          className="input-busca"
        />
      </div>

      <table className="tabela-tipos">
        <thead>
          <tr>
            <th>Nome</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {tiposFiltrados.map(tipo => (
            <tr key={tipo.id}>
              <td>{tipo.nome}</td>
              <td>
                <button className="btn-editar" onClick={() => abrirModal(tipo)}>Editar</button>
                {/* Botão Excluir futuro se precisar */}
              </td>
            </tr>
          ))}
        </tbody>
      </table>

      {modalAberto && (
        <EditTipoModal tipo={tipoEditando} fecharModal={fecharModal} />
      )}
    </div>
  );
};

export default PainelTipoServicos;
