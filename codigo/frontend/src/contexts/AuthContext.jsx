import { createContext, useState } from 'react';
import { getUserProfile } from '../services/AuthService'; // importa o serviço novo

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [username, setUsername] = useState('');
  const [token, setToken] = useState('');
  const [user, setUser] = useState(null); // Novo: guarda os dados completos do usuário

  const fetchUserProfile = async () => {
    try {
      const profile = await getUserProfile(); // chama a API
      setUser(profile); // salva no state
    } catch (error) {
      console.error('Erro ao buscar perfil do usuário:', error);
    }
  };

  const login = async (username, token) => {
    setIsAuthenticated(true);
    setUsername(username);

    await fetchUserProfile(); // Assim que logar, pega o perfil
  };

  const logout = () => {
    setIsAuthenticated(false);
    setUsername('');
    setUser(null); // limpa o perfil
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, username, token, user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
