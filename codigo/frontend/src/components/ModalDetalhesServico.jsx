

const ModalDetalhesServico = ({ servico, onClose }) => {
  if (!servico) return null; // Segurança

  return (
    <div className="modal-overlay">
      <div className="modal-content">
        <h2>Detalhes do Serviço</h2>
        <p><strong>Nome:</strong> {servico.nome}</p>
        <p><strong>Status:</strong> {servico.status}</p>
        <p><strong>Data:</strong> {new Date(servico.data).toLocaleDateString()}</p>
        <p><strong>Horário:</strong> {servico.horario}</p>
        <p><strong>Email:</strong> {servico.emailContato}</p>
        <p><strong>Telefone:</strong> {servico.telefoneContato}</p>
        <p><strong>Descrição:</strong> {servico.descricao}</p>

        <button onClick={onClose} className="fechar-btn">Fechar</button>
      </div>
    </div>
  );
};

export default ModalDetalhesServico;
