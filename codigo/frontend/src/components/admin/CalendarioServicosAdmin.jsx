import { Calendar, momentLocalizer } from "react-big-calendar";
import moment from "moment-timezone";
import "react-big-calendar/lib/css/react-big-calendar.css"; // Deixamos o básico do calendário
import { useState } from "react";
import ModalDetalhesServico from "../ModalDetalhesServico"; // Componente para mostrar detalhes do serviço

const localizer = momentLocalizer(moment);

const CalendarioServicosAdmin = ({ servicos }) => {
  const [servicoSelecionado, setServicoSelecionado] = useState(null);

  const eventos = servicos
    .filter(servico =>
      servico.data && servico.horario && 
      (servico.status === "ACEITO" || servico.status === "CONCLUIDO")
    )
    .map(servico => {
      const start = new Date(`${servico.data}T${servico.horario}`);
      const end = new Date(start.getTime() + 60 * 60 * 1000);

      return {
        id: servico.id,
        title: servico.nome,
        start,
        end,
        status: servico.status,
        ...servico, // Leva todos os dados para o evento
      };
    });

  return (
    <div>
      <Calendar
        localizer={localizer}
        events={eventos}
        startAccessor="start"
        endAccessor="end"
        style={{ height: 600 }}
        onSelectEvent={(event) => setServicoSelecionado(event)}
        messages={{
          next: "Próximo",
          previous: "Anterior",
          today: "Hoje",
          month: "Mês",
          week: "Semana",
          day: "Dia",
          agenda: "Agenda",
        }}
      />

      {servicoSelecionado && (
        <ModalDetalhesServico
          servico={servicoSelecionado}
          onClose={() => setServicoSelecionado(null)}
        />
      )}
    </div>
  );
};

export default CalendarioServicosAdmin;
