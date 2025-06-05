import { useContext } from 'react';
import { AuthContext } from '../contexts/AuthContext';
import { Link } from 'react-router-dom';

const Navbar = () => {
  const { isAuthenticated, username } = useContext(AuthContext);

  return (
    <nav className="navbar">
      <Link to="/">Home</Link>
      {!isAuthenticated ? (
        <>
          <Link to="/cadastro">Cadastro</Link>
          <Link to="/login">Login</Link>
        </>
      ) : (
        <Link to="/perfil">{username}</Link>
      )}
    </nav>
  );
};

export default Navbar;
