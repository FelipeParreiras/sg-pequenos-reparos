import { useState } from 'react';
import axios from 'axios';
import Input from '../components/Input';
import Button from '../components/Button';

const CadastroPage = () => {
  const [formData, setFormData] = useState({
    nome: '',
    email: '',
    telefone: '',
    username: '',
    tipo: 'CLIENTE',
    senha: ''
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/api/usuarios', formData);
      alert('Cadastro realizado com sucesso!');
    } catch (error) {
      alert('Erro no cadastro. Tente novamente.');
    }
  };

  return (
    <div className="cadastro-container">
      <h2>Cadastro</h2>
      <form onSubmit={handleSubmit} className="cadastro-form">
        <div className="form-group">
          <Input label="Nome" name="nome" value={formData.nome} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <Input label="Email" name="email" value={formData.email} onChange={handleChange} required type="email" />
        </div>
        <div className="form-group">
          <Input label="Telefone" name="telefone" value={formData.telefone} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <Input label="Username" name="username" value={formData.username} onChange={handleChange} required />
        </div>
        <div className="form-group">
          <label>Tipo:</label><br />
          <select name="tipo" value={formData.tipo} onChange={handleChange} className="form-select">
            <option value="CLIENTE">Cliente</option>
            <option value="ADMIN">Admin</option>
          </select>
        </div>
        <div className="form-group">
          <Input label="Senha" name="senha" value={formData.senha} onChange={handleChange} required type="password" />
        </div>
        <Button type="submit" className="form-button">Cadastrar</Button>
      </form>
    </div>
  );
};

export default CadastroPage;
