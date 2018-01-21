package nsdlib.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

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


/**
 * NS diagram reader for the XML-based format employed by Structorizer
 * (http://structorizer.fisch.lu/).
 */
public class StructorizerReader implements NSDReader
{
    @Override
    public NSDRoot read(InputStream in) throws NSDReaderException
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
            db.setErrorHandler(new QuietErrorHandler());
        } catch (ParserConfigurationException e) {
            throw new NSDReaderException(e);
        }

        Document d;
        try {
            d = db.parse(in);
        } catch (IOException | SAXException e) {
            throw new NSDReaderException(e);
        }

        return parseRoot(d.getDocumentElement());
    }

    private NSDRoot parseRoot(Element e) throws NSDReaderException
    {
        String label = deserialize(e.getAttribute("text")).get(0);
        NSDRoot root = new NSDRoot(label);

        Element children = (Element) e.getElementsByTagName("children").item(0);
        addChildren(root, children);

        return root;
    }

    /**
     * Finds all child elements of {@code e}, parses them and adds the parsed
     * instances to {@code cont}.
     *
     * @param cont The container to add the elements to.
     * @param e The element whose child elements shall be parsed.
     * @throws NSDReaderException If an unsupported tag is encountered.
     */
    private void addChildren(NSDContainer<NSDElement> cont, Element e)
            throws NSDReaderException
    {
        NodeList nodes = e.getChildNodes();

        for (int i = 0, n = nodes.getLength(); i < n; ++i) {
            Node node = nodes.item(i);
            if (!(node instanceof Element)) {
                continue;
            }
            cont.addChild(parse((Element) node));
        }
    }

    /**
     * Parses the given element according to its tag.
     *
     * @param e The element to parse.
     * @return The parsed element class instance.
     * @throws NSDReaderException If an unsupported tag is encountered.
     */
    private NSDElement parse(Element e) throws NSDReaderException
    {
        String tag = e.getTagName().toLowerCase();

        switch (tag) {
            case "instruction":
                String label = deserialize(e.getAttribute("text")).get(0);
                return new NSDInstruction(label);
            case "alternative":
                return parseDecision(e);
            case "case":
                return parseCase(e);
            case "forever":
                return parseForever(e);
            case "while":
                return parseTestFirstLoop(e);
            case "repeat":
                return parseTestLastLoop(e);
            case "parallel":
                return parseParallel(e);
        }

        throw new NSDReaderException("element unsupported: " + tag);
    }

    /**
     * Structorizer stores its string properties (e.g. "text") in a special
     * format, where lines are surrounded with quotation marks and separated by
     * commas. Quotes occurring inline as normal characters are escaped by
     * prefixing them with a second quote.
     *
     * <p>
     * This function reverses that process.
     *
     * <p>
     * Example: {@code "hello world","foo","\"\"bar\"\""} turns into
     * {@code ["hello world", "foo", "\"bar\""]} .
     *
     * @param s The string to deserialize.
     * @return The deserialized lines.
     */
    private List<String> deserialize(String s)
    {
        if (!s.startsWith("\"") || !s.endsWith("\"")) {
            return Arrays.asList(s);
        }

        List<String> strings = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        boolean open = false;

        for (int i = 0; i < s.length(); ++i) {
            char chr = s.charAt(i);
            if (chr == '\"') {
                if (i + 1 < s.length()) {
                    if (!open) {
                        open = true;
                    } else if (s.charAt(i + 1) == chr) {
                        sb.append(chr);
                        i++;
                    } else {
                        strings.add(sb.toString());
                        sb = new StringBuilder();
                        open = false;
                    }
                } else {
                    strings.add(sb.toString());
                    sb = new StringBuilder();
                    open = false;
                }
            } else if (open) {
                sb.append(chr);
            }
        }

        String tmp = sb.toString();
        if (!tmp.trim().isEmpty()) {
            strings.add(tmp);
        }

        return strings;
    }

    private NSDDecision parseDecision(Element e) throws NSDReaderException
    {
        String label = deserialize(e.getAttribute("text")).get(0);
        NSDDecision dec = new NSDDecision(label);

        Element qTrue = (Element) e.getElementsByTagName("qTrue").item(0);
        addChildren(dec.getThen(), qTrue);

        Element qFalse = (Element) e.getElementsByTagName("qFalse").item(0);
        addChildren(dec.getElse(), qFalse);

        return dec;
    }

    private NSDCase parseCase(Element e) throws NSDReaderException
    {
        List<String> lines = deserialize(e.getAttribute("text"));
        NSDCase cas = new NSDCase(lines.get(0));

        NodeList qCase = e.getElementsByTagName("qCase");
        for (int i = 0; i < qCase.getLength(); ++i) {

            Node node = qCase.item(i);
            if (!(node instanceof Element)) {
                continue;
            }

            NSDContainer<NSDElement> cont = new NSDContainer<NSDElement>(
                    lines.get(cas.countChildren() + 1));
            addChildren(cont, (Element) node);
            cas.addChild(cont);

        }

        return cas;
    }

    private NSDForever parseForever(Element e) throws NSDReaderException
    {
        NSDForever loop = new NSDForever();

        Element qForever = (Element) e.getElementsByTagName("qForever").item(0);
        addChildren(loop, qForever);

        return loop;
    }

    private NSDTestFirstLoop parseTestFirstLoop(Element e)
            throws NSDReaderException
    {
        String label = deserialize(e.getAttribute("text")).get(0);
        NSDTestFirstLoop loop = new NSDTestFirstLoop(label);

        Element qWhile = (Element) e.getElementsByTagName("qWhile").item(0);
        addChildren(loop, qWhile);

        return loop;
    }

    private NSDTestLastLoop parseTestLastLoop(Element e)
            throws NSDReaderException
    {
        String label = deserialize(e.getAttribute("text")).get(0);
        NSDTestLastLoop loop = new NSDTestLastLoop(label);

        Element qRepeat = (Element) e.getElementsByTagName("qRepeat").item(0);
        addChildren(loop, qRepeat);

        return loop;
    }

    private NSDParallel parseParallel(Element e) throws NSDReaderException
    {
        NSDParallel parallel = new NSDParallel();

        NodeList qPara = e.getElementsByTagName("qPara");
        for (int i = 0; i < qPara.getLength(); ++i) {

            Node node = qPara.item(i);
            if (!(node instanceof Element)) {
                continue;
            }

            NSDContainer<NSDElement> cont = new NSDContainer<>("");
            addChildren(cont, (Element) node);
            parallel.addChild(cont);

        }

        return parallel;
    }

    /**
     * The default DocumentBuilder error handler prints its exceptions, even
     * when they are caught. This is a replacement handler that does not do that
     * and instead simply rethrows the errors. Warnings are ignored.
     */
    private static class QuietErrorHandler implements ErrorHandler
    {
        @Override
        public void warning(SAXParseException exception) throws SAXException
        {
        }

        @Override
        public void error(SAXParseException exception) throws SAXException
        {
            throw exception;
        }

        @Override
        public void fatalError(SAXParseException exception) throws SAXException
        {
            throw exception;
        }
    }
}
