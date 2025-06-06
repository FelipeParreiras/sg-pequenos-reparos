import React, { useState } from 'react';

const diasDaSemana = [
  'Segunda-feira',
  'Terça-feira',
  'Quarta-feira',
  'Quinta-feira',
  'Sexta-feira',
  'Sábado',
  'Domingo',
];

const PainelItinerario = () => {
  const [tipo, setTipo] = useState('SEMANAL'); // SEMANAL ou CICLICO
  const [diasSelecionados, setDiasSelecionados] = useState([]);
  const [diasTrabalho, setDiasTrabalho] = useState(4);
  const [diasFolga, setDiasFolga] = useState(4);
  const [observacao, setObservacao] = useState('');

  const toggleDia = (dia) => {
    if (diasSelecionados.includes(dia)) {
      setDiasSelecionados(diasSelecionados.filter(d => d !== dia));
    } else {
      setDiasSelecionados([...diasSelecionados, dia]);
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const config = {
      tipo,
      dias: tipo === 'SEMANAL' ? diasSelecionados : [],
      diasTrabalho: tipo === 'CICLICO' ? diasTrabalho : null,
      diasFolga: tipo === 'CICLICO' ? diasFolga : null,
      observacao,
    };
    console.log('Configuração enviada:', config);
    // 🚀 Futuro: Aqui chamaremos o itinerarioService
  };

  return (
    <div className="painel-itinerario-container">
      <h2>Configuração de Itinerário</h2>
      <form onSubmit={handleSubmit} className="form-itinerario">
        <div className="form-group">
          <label>Tipo de Configuração:</label>
          <select value={tipo} onChange={(e) => setTipo(e.target.value)} className="select-tipo">
            <option value="SEMANAL">Dias Fixos da Semana</option>
            <option value="CICLICO">Escala Cíclica (Ex: 4 dias sim / 4 dias não)</option>
          </select>
        </div>

        {tipo === 'SEMANAL' && (
          <div className="form-group-dias">
            <label>Selecionar Dias:</label>
            <div className="dias-checkboxes">
              {diasDaSemana.map((dia) => (
                <label key={dia} className="checkbox-dia">
                  <input
                    type="checkbox"
                    checked={diasSelecionados.includes(dia)}
                    onChange={() => toggleDia(dia)}
                  />
                  {dia}
                </label>
              ))}
            </div>
          </div>
        )}

        {tipo === 'CICLICO' && (
          <div className="form-group-ciclo">
            <label>Dias de Trabalho:</label>
            <input
              type="number"
              value={diasTrabalho}
              onChange={(e) => setDiasTrabalho(Number(e.target.value))}
              min="1"
              className="input-ciclo"
            />
            <label>Dias de Folga:</label>
            <input
              type="number"
              value={diasFolga}
              onChange={(e) => setDiasFolga(Number(e.target.value))}
              min="1"
              className="input-ciclo"
            />
          </div>
        )}

        <div className="form-group">
          <label>Observação:</label>
          <textarea
            value={observacao}
            onChange={(e) => setObservacao(e.target.value)}
            rows="4"
            placeholder="Adicione observações adicionais..."
            className="textarea-observacao"
          />
        </div>

        <div className="form-acoes">
          <button type="submit" className="btn-salvar">Salvar Configuração</button>
        </div>
      </form>
    </div>
  );
};

export default PainelItinerario;
