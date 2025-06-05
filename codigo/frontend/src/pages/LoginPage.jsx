import { useContext, useState } from 'react';
import { AuthContext } from '../contexts/AuthContext';
import { login as loginService } from '../services/AuthService';
import { useNavigate } from 'react-router-dom';
import Input from '../components/Input';
import Button from '../components/Button';

const LoginPage = () => {
  const { login } = useContext(AuthContext);
  const navigate = useNavigate();

  const [formData, setFormData] = useState({ username: '', senha: '' });
  const [error, setError] = useState('');

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    try {
      const data = await loginService(formData.username, formData.senha);
      login(formData.username, data.token);
      navigate('/');
    } catch (err) {
      setError('Usuário ou senha inválidos');
    }
  };

  return (
    <div>
      <h2>Login</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <form onSubmit={handleSubmit}>
          <Input label="Nome de Usuário" type="text" name="username" value={formData.username} onChange={handleChange} required />
          <Input label="Senha"type="password" name="senha" value={formData.senha} onChange={handleChange} required />
        <Button type="submit">Entrar</Button>
      </form>
    </div>
  );
};

export default LoginPage;
