import React, { useState } from 'react';
import { criarTipo, atualizarTipo } from '../services/tipoService';

const EditTipoModal = ({ tipo, fecharModal }) => {
  const [nome, setNome] = useState(tipo ? tipo.nome : '');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      if (tipo) {
        await atualizarTipo(tipo.id, { nome });
      } else {
        await criarTipo({ nome });
      }
      fecharModal();
    } catch (error) {
      console.error('Erro ao salvar tipo de serviço:', error);
    }
  };

  return (
    <div className="modal-overlay">
      <div className="modal-conteudo">
        <h2>{tipo ? 'Editar Tipo de Serviço' : 'Adicionar Tipo de Serviço'}</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Nome</label>
            <input
              type="text"
              value={nome}
              onChange={(e) => setNome(e.target.value)}
              required
            />
          </div>
          <div className="modal-acoes">
            <button type="submit" className="btn-salvar">Salvar</button>
            <button type="button" className="btn-cancelar" onClick={fecharModal}>Cancelar</button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default EditTipoModal;
