import { useState } from 'react';
import { updateUser } from '../services/AuthService';
import Input from '../components/Input';
import Button from '../components/Button';

const EditUserModal = ({ user, onClose }) => {
  const [formData, setFormData] = useState({
    nome: user.nome || '',
    email: user.email || '',
    telefone: user.telefone || '',
    username: user.username || '',
    senha: ''
  });

  const handleChange = (e) => {
    setFormData({...formData, [e.target.name]: e.target.value});
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await updateUser(formData);
      alert('Perfil atualizado com sucesso!');
      onClose();
    } catch (error) {
      console.error('Erro ao atualizar perfil:', error);
      alert('Falha ao atualizar perfil!');
    }
  };

  return (
    <div style={{
      position: 'fixed', top: '50%', left: '50%', transform: 'translate(-50%, -50%)',
      backgroundColor: 'white', padding: '20px', boxShadow: '0 0 10px rgba(0,0,0,0.3)', zIndex: 1000
    }}>
      <h2>Editar Perfil</h2>
      <form onSubmit={handleSubmit}>
        <Input label="Nome" name="nome" value={formData.nome} onChange={handleChange} required />
        <Input label="Email" name="email" value={formData.email} onChange={handleChange} required type="email" />
        <Input label="Telefone" name="telefone" value={formData.telefone} onChange={handleChange} required />
        <Input label="Nome de Usuário" name="username" value={formData.username} onChange={handleChange} required />
        <Input label="Senha" name="senha" value={formData.senha} onChange={handleChange} required type="password" />
        .
        <Button type="submit">Salvar</Button>
        <Button type="button" onClick={onClose}>Cancelar</Button>
      </form>
    </div>
  );
};

export default EditUserModal;
