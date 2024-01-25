
import static org.junit.Assert.*;

import org.example.model.Acao;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class UserTest {

    @Test
    public void testRegisterUser() {

        String input = "João\nSeguro123\nMasculino\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Acao acao = new Acao();

        acao.registerUser();

        assertEquals(1, acao.getUser().size());
        assertEquals("João", acao.getUser().get(0).getNome());
        assertEquals("Seguro123", acao.getUser().get(0).getDadosMedicos());
        assertEquals("Masculino", acao.getUser().get(0).getDadosPessoais());
    }

    @Test
    public void testApprovedUser() {

        String input = "João\nSeguro123\nMasculino\nJoão";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Acao acao = new Acao();

        acao.registerUser();

        acao.AprovarCliente();
        
        
        assertEquals(1, acao.getUser().size());
        assertEquals("João", acao.getUser().get(0).getNome());
        assertEquals("Seguro123", acao.getUser().get(0).getDadosMedicos());
        assertEquals("Masculino", acao.getUser().get(0).getDadosPessoais());
        assertEquals(true, acao.getUser().get(0).getAprovado());

    }

    @Test
    public void testRegisterProf(){
        String input = "João\nMasculino\nMédico\nCardiologia\n2003-19-07\nMedicare\n20";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Acao acao = new Acao();
        
        acao.registerProf();

        
        assertEquals(1, acao.getProfissionais().size());
        assertEquals("João", acao.getProfissionais().get(0).getNome());
        assertEquals("Masculino", acao.getProfissionais().get(0).getDados_pessoais());
        assertEquals("Médico", acao.getProfissionais().get(0).getDados_medicos());
        assertEquals("Cardiologia", acao.getProfissionais().get(0).getEspecialidade());
        assertEquals("2003-19-07", acao.getProfissionais().get(0).getData());
        assertEquals("Medicare", acao.getProfissionais().get(0).getSeguro());
        assertEquals(20, acao.getProfissionais().get(0).getPreco(),0);

    }

    @Test
    public void testApprovedProf(){
        String input = "João\nMasculino\nMédico\nCardiologia\n2003-19-07\nMedicare\n20\nJoão";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Acao acao = new Acao();
        
        acao.registerProf();
        acao.aprovarProf();
        
        assertEquals(1, acao.getProfissionais().size());
        assertEquals("João", acao.getProfissionais().get(0).getNome());
        assertEquals("Masculino", acao.getProfissionais().get(0).getDados_pessoais());
        assertEquals("Médico", acao.getProfissionais().get(0).getDados_medicos());
        assertEquals("Cardiologia", acao.getProfissionais().get(0).getEspecialidade());
        assertEquals("2003-19-07", acao.getProfissionais().get(0).getData());
        assertEquals("Medicare", acao.getProfissionais().get(0).getSeguro());
        assertEquals(20, acao.getProfissionais().get(0).getPreco(),0);
        assertEquals(true, acao.getProfissionais().get(0).getAprovado());


    }

    @Test
    public void testAgendarConsulta(){
        String input = "João\nMasculino\nMédico\nCardiologia\n2003-19-07\nMedicare\n20\nJoão\nMiguel\nSeguro123\nMasculino\nMiguel\nMiguel\nJoão\n2003-19-07\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        
        System.setIn(in);
        Acao acao = new Acao();
        acao.registerProf();
        acao.aprovarProf();
        acao.registerUser();
        acao.AprovarCliente();
        acao.agendarconsulta();
        

        assertEquals(1, acao.getProfissionais().size());
        assertEquals("João", acao.getProfissionais().get(0).getNome());
        assertEquals("Masculino", acao.getProfissionais().get(0).getDados_pessoais());
        assertEquals("Médico", acao.getProfissionais().get(0).getDados_medicos());
        assertEquals("Cardiologia", acao.getProfissionais().get(0).getEspecialidade());
        assertEquals("2003-19-07", acao.getProfissionais().get(0).getData());
        assertEquals("Medicare", acao.getProfissionais().get(0).getSeguro());
        assertEquals(20, acao.getProfissionais().get(0).getPreco(),0);
        assertEquals(true, acao.getProfissionais().get(0).getAprovado());
        

        assertEquals(1, acao.getUser().size());
        assertEquals("Miguel", acao.getUser().get(0).getNome());
        assertEquals("Seguro123", acao.getUser().get(0).getDadosMedicos());
        assertEquals("Masculino", acao.getUser().get(0).getDadosPessoais());
        assertEquals(true, acao.getUser().get(0).getAprovado());


        assertEquals(1, acao.getConsultas().size());
        assertEquals("João", acao.getConsultas().get(0).getNome_prof());
        assertEquals("Miguel", acao.getConsultas().get(0).getNome_cliente());
        assertEquals("2003-19-07", acao.getConsultas().get(0).getData());
        

    }

    @Test
    public void testConfirmar_Consultas(){
        String input = "João\nMasculino\nMédico\nCardiologia\n2003-19-07\nMedicare\n20\nJoão\nMiguel\nSeguro123\nMasculino\nMiguel\nMiguel\nJoão\n2003-19-07\nJoão\nMiguel\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        
        System.setIn(in);
        Acao acao = new Acao();
        acao.registerProf();
        acao.aprovarProf();
        acao.registerUser();
        acao.AprovarCliente();
        acao.agendarconsulta();
        acao.apresentarServico();
        

        assertEquals(1, acao.getProfissionais().size());
        assertEquals("João", acao.getProfissionais().get(0).getNome());
        assertEquals("Masculino", acao.getProfissionais().get(0).getDados_pessoais());
        assertEquals("Médico", acao.getProfissionais().get(0).getDados_medicos());
        assertEquals("Cardiologia", acao.getProfissionais().get(0).getEspecialidade());
        assertEquals("2003-19-07", acao.getProfissionais().get(0).getData());
        assertEquals("Medicare", acao.getProfissionais().get(0).getSeguro());
        assertEquals(20, acao.getProfissionais().get(0).getPreco(),0);
        assertEquals(true, acao.getProfissionais().get(0).getAprovado());
        

        assertEquals(1, acao.getUser().size());
        assertEquals("Miguel", acao.getUser().get(0).getNome());
        assertEquals("Seguro123", acao.getUser().get(0).getDadosMedicos());
        assertEquals("Masculino", acao.getUser().get(0).getDadosPessoais());
        assertEquals(true, acao.getUser().get(0).getAprovado());


        assertEquals(1, acao.getConsultas().size());
        assertEquals("João", acao.getConsultas().get(0).getNome_prof());
        assertEquals("Miguel", acao.getConsultas().get(0).getNome_cliente());
        assertEquals("2003-19-07", acao.getConsultas().get(0).getData());
        assertEquals(true, acao.getConsultas().get(0).getAprovado());
        
    }


}