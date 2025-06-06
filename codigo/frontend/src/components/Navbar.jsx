import { useContext } from 'react';
import { AuthContext } from '../contexts/AuthContext';
import { Link } from 'react-router-dom';

const Navbar = () => {
  const { isAuthenticated, username, user } = useContext(AuthContext); // tipo: ADMIN ou CLIENTE

  return (
    <nav className="navbar">
      <Link to="/">SG Pequenos Reparos</Link>

      {!isAuthenticated ? (
        <>
          <Link to="/cadastro">Cadastro</Link>
          <Link to="/login">Login</Link>
        </>
      ) : (
        <>
          {user?.tipo === 'ADMIN' && (
            <Link to="/admin/painel">Painel</Link>
          )}
          <Link to="/perfil">Olá, {username}!</Link>
        </>
      )}
    </nav>
  );
};

export default Navbar;
