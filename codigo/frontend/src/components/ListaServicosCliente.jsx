import { useState } from 'react';
import { cancelarServico } from '../services/servicoService';
import CardServico from './CardServico';

const ListaServicosCliente = ({ servicos, onServicoAtualizado }) => {
  const [cancelandoId, setCancelandoId] = useState(null);

  const handleCancelar = async (id) => {
    const motivo = prompt('Digite o motivo do cancelamento:');
    if (!motivo) {
      alert('Cancelamento abortado. Motivo é obrigatório.');
      return;
    }

    setCancelandoId(id);
    try {
      await cancelarServico(id, motivo);
      alert('Serviço cancelado com sucesso!');
      if (onServicoAtualizado) {
        onServicoAtualizado();
      }
    } catch (error) {
      console.error('Erro ao cancelar serviço:', error);
      alert('Erro ao cancelar serviço.');
    } finally {
      setCancelandoId(null);
    }
  };

  const solicitados = servicos.filter(s => s.status === 'SOLICITADO');
  const agendados = servicos.filter(s => s.status === 'ACEITO' && s.data && s.horario);
  const concluidos = servicos.filter(s => s.status === 'CONCLUIDO');

  return (
    <div>
      <h2>Minhas Solicitações</h2>

      <section>
        <h3>Serviços Solicitados</h3>
        {solicitados.length === 0 ? (
          <p>Você não tem serviços solicitados.</p>
        ) : (
          solicitados.map(servico => (
            <CardServico
              key={servico.id}
              servico={servico}
              tipo="solicitado"
              onCancelar={() => handleCancelar(servico.id)}
              cancelando={cancelandoId === servico.id}
            />
          ))
        )}
      </section>

      <section style={{ marginTop: '30px' }}>
        <h3>Serviços Agendados</h3>
        {agendados.length === 0 ? (
          <p>Você não tem serviços agendados.</p>
        ) : (
          agendados.map(servico => (
            <CardServico
              key={servico.id}
              servico={servico}
              tipo="agendado"
            />
          ))
        )}
      </section>

      <section style={{ marginTop: '30px' }}>
        <h3>Serviços Concluídos</h3>
        {concluidos.length === 0 ? (
          <p>Você ainda não tem serviços concluídos.</p>
        ) : (
          concluidos.map(servico => (
            <CardServico
              key={servico.id}
              servico={servico}
              tipo="concluido"
            />
          ))
        )}
      </section>
    </div>
  );
};

export default ListaServicosCliente;
