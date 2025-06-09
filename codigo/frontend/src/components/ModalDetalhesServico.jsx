const ModalDetalhesServico = ({ servico, onClose, usuario }) => {
  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <h2>Detalhes do Serviço</h2>

        <p><strong>Nome:</strong> {servico.nome}</p>
        <p><strong>Tipo:</strong> {typeof servico.tipoServico === 'string' ? servico.tipoServico : servico.tipoServico?.nome}</p>
        <p><strong>Descrição:</strong> {servico.descricao || '—'}</p>
        <p><strong>Status:</strong> {servico.status}</p>
        <p><strong>Data:</strong> {servico.data ? new Date(servico.data).toLocaleDateString() : 'Não agendada'}</p>
        <p><strong>Horário:</strong> {servico.horario || '—'}</p>
        <p><strong>Email para contato:</strong> {servico.emailContato}</p>
        <p><strong>Telefone:</strong> {servico.telefoneContato}</p>
        <p><strong>Dias disponíveis do cliente:</strong> {servico.diasDisponiveisCliente?.join(', ')}</p>
        <p><strong>Turno disponível do cliente:</strong> {servico.periodoDisponivelCliente}</p>

        {usuario?.tipo === 'CLIENTE' && servico.administradorNome && (
          <p><strong>Prestador:</strong> {servico.administradorNome}</p>
        )}
        {usuario?.tipo === 'ADMIN' && servico.clienteNome && (
          <p><strong>Cliente:</strong> {servico.clienteNome}</p>
        )}

        <div className="modal-buttons">
          <button onClick={onClose}>Fechar</button>
        </div>
      </div>
    </div>
  );
};

export default ModalDetalhesServico;
