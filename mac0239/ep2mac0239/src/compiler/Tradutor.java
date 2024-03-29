/* Generated By:JavaCC: Do not edit this line. Tradutor.java */
package compiler;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;

class Tradutor implements TradutorConstants {
  ArrayList < Clausula > clausulas;
  TabelaVariaveis global;
  GeradorCnf gerCnf;

  public static void main(String [] args) throws ParseException, TokenMgrError
  {
    String opcao = "", file = "";
    Tradutor parser = null;
    if (args.length > 0)
    {
      if (args.length == 1)
      {
        if (args [0].equals("-c"))
        {
          opcao = args [0];
        }
        else
        {
          file = args [0];
        }
      }
      else if (args.length == 2)
      {
        opcao = args [0];
        file = args [1];
      }
    }
    if (!file.equals(""))
    {
      try
      {
        parser = new Tradutor(new FileInputStream(System.getProperty("user.dir") + "/src/compiler/" + file));
      }
      catch (FileNotFoundException e)
      {
        // TODO Auto-generated catch block        e.printStackTrace();
      }
    }
    else
    {
      try
      {
        parser = new Tradutor(new FileInputStream(System.getProperty("user.dir") + "/src/compiler/input.txt"));
      }
      catch (FileNotFoundException e)
      {
        // TODO Auto-generated catch block        e.printStackTrace();
      }
    }
    parser.init();
    parser.Start();
    if (opcao.equals("-c"))
    {
      parser.getGerCnf().setState(true);
    }
    parser.printClausulas();
    /*parser.printaDeclaracoes();*/
  }
  GeradorCnf getGerCnf()
  {
    return gerCnf;
  }

  void init()
  {
    clausulas = new ArrayList < Clausula > ();
    global = new TabelaVariaveis();
    gerCnf = new GeradorCnf(clausulas);
  }

  void printaDeclaracoes()
  {
    for (Entry < String, Incrementador > entry : global.getMap().entrySet())
    {
      String key = entry.getKey();
      System.out.print(key + ":");
      System.out.print(global.getMap().get(key).getInicio());
      System.out.println(" " + global.getMap().get(key).getInicio());
    }
  }

  void printClausulas()
  {
    for (Clausula c : clausulas)
    {
      c.print();
    }
  }

/*void operacao(Restricao r, String dir) :{  Token t = null;  Token op = null;}{  t = < VARIABLE >  {    r.addRestricao(t.image, "+",dir);  }  (    op = < OPERADOR >    {}    t = < VARIABLE >    {      r.addRestricao(t.image, op.image,dir);    }  )*}*/
  final public void operacao(Restricao r, String dir) throws ParseException {
  Token t = null;
  Token op = null;
    t = jj_consume_token(VARIABLE);
    r.addRestricao(t.image, "+", dir);
    label_1:
    while (true) {
      if (jj_2_1(2)) {
        ;
      } else {
        break label_1;
      }
      op = jj_consume_token(OPERADOR);

      t = jj_consume_token(VARIABLE);
      r.addRestricao(t.image, op.image, dir);
    }
  }

  final public void restricao(Clausula c) throws ParseException {
  Token t = null;
  Restricao r;
    r = new Restricao();
    /*System.out.print(" ");*/

    operacao(r, "esq");
    t = jj_consume_token(COMPARADOR);
    r.setComparador(t.image);
    c.addRestricao(r);
    /*System.out.print(" " + t + " ");*/

    operacao(r, "dir");
  }

  final public void argumento(Predicado p) throws ParseException {
  Token t, t2;
  t2 = null;
  Predicado pred = p;
    t = jj_consume_token(VARIABLE);
    pred.addArg(t.image, global.getMap().get(t.image));
    /*System.out.print(t);*/

    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 12:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_2;
      }
      jj_consume_token(12);
      t2 = jj_consume_token(VARIABLE);
      if (t2 != null)
      {
        p.addArg(t2.image, global.getMap().get(t2.image));
        /*System.out.print("," + t2);*/
      }
    }
  }

  final public void predicado(Clausula c) throws ParseException {
  Token t;
  Predicado p;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case OPERADOR:
      jj_consume_token(OPERADOR);
      t = jj_consume_token(PREDID);
    p = new Predicado(t.image);
    p.setNegado(true);
    c.addPredicado(p);
    /*System.out.print("-" + t);*/

      jj_consume_token(OPEN_PAR);

      argumento(p);

      jj_consume_token(CLOSE_PAR);
      break;
    case PREDID:
      t = jj_consume_token(PREDID);
    p = new Predicado(t.image);
    c.addPredicado(p);
    /*System.out.print(t);*/

      jj_consume_token(OPEN_PAR);

      argumento(p);

      jj_consume_token(CLOSE_PAR);
      break;
    default:
      jj_la1[1] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void clausula() throws ParseException {
  Clausula c = new Clausula(gerCnf);
  clausulas.add(c);
    predicado(c);
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case OPERADOR:
      case PREDID:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_3;
      }
      predicado(c);
    }
    jj_consume_token(13);

    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case VARIABLE:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_4;
      }
      restricao(c);
    }

  }

  final public void declaracao() throws ParseException {
  Token t, t1, t2;
    t = jj_consume_token(VARIABLE);

    jj_consume_token(14);
    t1 = jj_consume_token(NUMBER);

    t2 = jj_consume_token(NUMBER);

    jj_consume_token(13);
    Incrementador inc = new Incrementador(t.image, Integer.parseInt(t1.image), Integer.parseInt(t2.image));
    global.addToTable(t.image, inc);
  }

  final public void Start() throws ParseException {
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case VARIABLE:
        ;
        break;
      default:
        jj_la1[4] = jj_gen;
        break label_5;
      }
      declaracao();
    }
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case OPERADOR:
      case PREDID:
        ;
        break;
      default:
        jj_la1[5] = jj_gen;
        break label_6;
      }
      clausula();
    }
    jj_consume_token(0);
  }

  private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_3_1() {
    if (jj_scan_token(OPERADOR)) return true;
    if (jj_scan_token(VARIABLE)) return true;
    return false;
  }

  /** Generated Token Manager. */
  public TradutorTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[6];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x1000,0xa00,0xa00,0x40,0x40,0xa00,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[1];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public Tradutor(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public Tradutor(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new TradutorTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public Tradutor(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new TradutorTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public Tradutor(TradutorTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(TradutorTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 6; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[15];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 6; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 15; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 1; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

}
