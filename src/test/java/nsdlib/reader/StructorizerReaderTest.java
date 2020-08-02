package nsdlib.reader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import nsdlib.elements.NSDContainer;
import nsdlib.elements.NSDElement;
import nsdlib.elements.NSDInstruction;
import nsdlib.elements.NSDRoot;
import nsdlib.elements.alternatives.NSDCase;
import nsdlib.elements.alternatives.NSDDecision;
import nsdlib.elements.loops.NSDForever;
import nsdlib.elements.loops.NSDTestFirstLoop;
import nsdlib.elements.loops.NSDTestLastLoop;
import nsdlib.elements.parallel.NSDParallel;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class StructorizerReaderTest
{
    @Test
    public void throwsNSDReaderExceptionForInvalidInput()
    {
        StructorizerReader obj = new StructorizerReader();
        assertThrows(NSDReaderException.class, () -> {
            obj.read(new ByteArrayInputStream("<root".getBytes(StandardCharsets.UTF_8)));
        });
    }

    @Test
    public void doesNotPrintOnError()
    {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        PrintStream str = new PrintStream(bout);

        PrintStream prevOut = System.out, prevErr = System.err;
        System.setOut(str);
        System.setErr(str);

        StructorizerReader obj = new StructorizerReader();
        try {
            obj.read(new ByteArrayInputStream("<root".getBytes(StandardCharsets.UTF_8)));
        } catch (Exception ignored) {
        }
        str.flush();

        assertEquals(0, bout.size());

        System.setOut(prevOut);
        System.setErr(prevErr);
    }

    private InputStream open(String name) throws FileNotFoundException
    {
        File f = new File("./src/test/resources/structorizer/" + name + ".nsd");
        return new FileInputStream(f);
    }

    @Test
    public void parsesBasic() throws NSDReaderException, IOException
    {
        // resource file: structorizer/basic.nsd

        try (InputStream in = open("basic")) {
            NSDRoot result = new StructorizerReader().read(in);

            assertEquals("operation", result.getLabel());
            assertEquals(1, result.countChildren());

            NSDInstruction instr = (NSDInstruction) result.getChild(0);
            assertEquals("basic instruction", instr.getLabel());
        }
    }

    @Test
    public void parsesLabelsWithQuotes() throws NSDReaderException, IOException
    {
        // resource file: structorizer/labels-with-quotes.nsd

        try (InputStream in = open("labels-with-quotes")) {
            NSDRoot result = new StructorizerReader().read(in);

            assertEquals("operation \"foobar\"", result.getLabel());
            assertEquals(2, result.countChildren());

            NSDInstruction instr0 = (NSDInstruction) result.getChild(0);
            assertEquals("basic instruction (42, \"qux\")", instr0.getLabel());

            NSDInstruction instr1 = (NSDInstruction) result.getChild(1);
            assertEquals("double \"\" triple \"\"\" quadruple \"\"\"\" quotes", instr1.getLabel());
        }
    }

    @Test
    public void parsesAlternative() throws NSDReaderException, IOException
    {
        // resource file: structorizer/alternative.nsd

        try (InputStream in = open("alternative")) {
            NSDRoot result = new StructorizerReader().read(in);

            NSDDecision alt = (NSDDecision) result.getChild(0);
            assertEquals("something true?", alt.getLabel());

            assertEquals(1, alt.getThen().countChildren());
            assertEquals("say \"yeah!\"", alt.getThen().getChild(0).getLabel());

            assertEquals(1, alt.getElse().countChildren());
            assertEquals("say \"nope!\"", alt.getElse().getChild(0).getLabel());
        }
    }

    @Test
    public void parsesCase() throws NSDReaderException, IOException
    {
        // resource file: structorizer/case.nsd

        try (InputStream in = open("case")) {
            NSDRoot result = new StructorizerReader().read(in);

            NSDCase alt = (NSDCase) result.getChild(0);
            assertEquals("variable", alt.getLabel());
            assertEquals(3, alt.countChildren());

            assertEquals("a", alt.getChild(0).getLabel());
            assertEquals("b", alt.getChild(1).getLabel());
            assertEquals("default", alt.getChild(2).getLabel());
        }
    }

    @Test
    public void parsesLoops() throws NSDReaderException, IOException
    {
        // resource file: structorizer/loops.nsd

        try (InputStream in = open("loops")) {
            NSDRoot result = new StructorizerReader().read(in);

            assertEquals(3, result.countChildren());

            assertTrue(result.getChild(0) instanceof NSDForever);
            assertEquals("do something forever", ((NSDForever) result.getChild(0)).getChild(0).getLabel());

            assertTrue(result.getChild(1) instanceof NSDTestFirstLoop);
            assertEquals("do something while", ((NSDTestFirstLoop) result.getChild(1)).getChild(0).getLabel());

            assertTrue(result.getChild(2) instanceof NSDTestLastLoop);
            assertEquals("do something until", ((NSDTestLastLoop) result.getChild(2)).getChild(0).getLabel());
        }
    }

    @Test
    public void parsesParallel() throws NSDReaderException, IOException
    {
        // resource file: structorizer/parallel.nsd

        try (InputStream in = open("parallel")) {
            NSDRoot result = new StructorizerReader().read(in);

            NSDParallel para = (NSDParallel) result.getChild(0);
            assertEquals(2, para.countChildren());

            NSDContainer<NSDElement> para0 = para.getChild(0);
            assertEquals("parallel 1", para0.getChild(0).getLabel());

            NSDContainer<NSDElement> para1 = para.getChild(1);
            assertEquals("parallel 2", para1.getChild(0).getLabel());
        }
    }

    @Test
    public void throwsForUnsupportedElement() throws IOException
    {
        // resource file: structorizer/unsupported.nsd

        try (InputStream in = open("unsupported")) {
            assertThrows(NSDReaderException.class, () -> new StructorizerReader().read(in));
        }
    }
}
