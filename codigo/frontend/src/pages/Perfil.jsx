import { useContext } from 'react';
import { AuthContext } from '../contexts/AuthContext';

const PerfilPage = () => {
  const { username, logout } = useContext(AuthContext);

  const handleLogout = () => {
    logout();
  };

  return (
    <div>
      <h1>Perfil de {username}</h1>
      <button onClick={handleLogout}>Logout</button>
    </div>
  );
};

export default PerfilPage;
