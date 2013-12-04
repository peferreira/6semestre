/* Generated By:JavaCC: Do not edit this line. Tradutor.java */
package compiler;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
class Tradutor implements TradutorConstants {
  ArrayList < Clausula > clausulas;
  TabelaVariaveis global;

  public static void main(String [] args) throws ParseException, TokenMgrError
  {
    Tradutor parser = null;
    try
    {
      parser = new Tradutor(new FileInputStream("/home/pedro/workspace/ep2mac0239/src/compiler/input.txt"));
    }
    catch (FileNotFoundException e)
    {
      // TODO Auto-generated catch block
    }
    parser.init();
    parser.Start();
    parser.printClausulas();
    /*parser.printaDeclaracoes();*/
  }

  void init()
  {
    clausulas = new ArrayList < Clausula > ();
    global = new TabelaVariaveis();
  }
  void printaDeclaracoes()
  {
        for (Entry<String, Incrementador> entry : global.getMap().entrySet()) {
                        String key = entry.getKey();
                        System.out.print(key+":");
                        System.out.print(global.getMap().get(key).getInicio());
                        System.out.println(" "+global.getMap().get(key).getInicio());

                }

  }
  void printClausulas()
  {
    for (Clausula c : clausulas)
    {
      c.print();
    }
  }

  final public void operacaoEsq(Restricao r) throws ParseException {
  Token t = null;
  Token op = null;
    t = jj_consume_token(VARIABLE);
    r.addEsq(t.image, "+");
    System.out.print(t);
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case OPERADOR:
        ;
        break;
      default:
        jj_la1[0] = jj_gen;
        break label_1;
      }
      op = jj_consume_token(OPERADOR);
      System.out.print(op.image);
      t = jj_consume_token(VARIABLE);
      r.addEsq(t.image, op.image);
      System.out.print(t);
    }
  }

  final public void operacaoDir(Restricao r) throws ParseException {
  Token t = null;
  Token op = null;
    t = jj_consume_token(VARIABLE);
    r.addDir(t.image, "+");
    System.out.print(t);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case OPERADOR:
        ;
        break;
      default:
        jj_la1[1] = jj_gen;
        break label_2;
      }
      op = jj_consume_token(OPERADOR);
      System.out.print(op.image);
      t = jj_consume_token(VARIABLE);
      if (t != null)
      {
        r.addDir(t.image, op.image);
        System.out.print(t);
      }
    }
  }

  final public void restricao(Clausula c) throws ParseException {
  Token t = null;
  Restricao r;
    r = new Restricao();
    System.out.print(" ");
    operacaoEsq(r);
    t = jj_consume_token(COMPARADOR);
    r.setComparador(t.image);
    c.addRestricao(r);
    System.out.print(" " + t + " ");
    operacaoDir(r);
  }

  final public void argumento(Predicado p) throws ParseException {
  Token t, t2;
  t2 = null;
  Predicado pred = p;
    t = jj_consume_token(VARIABLE);
    pred.addArg(t.image, global.getMap().get(t.image));
    System.out.print(t);
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 12:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_3;
      }
      jj_consume_token(12);
      t2 = jj_consume_token(VARIABLE);
      if (t2 != null)
      {
        p.addArg(t2.image, global.getMap().get(t2.image));
        System.out.print("," + t2);
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
    System.out.print("-" + t);
      jj_consume_token(OPEN_PAR);
    System.out.print("(");
      argumento(p);
    System.out.print(") ");
      jj_consume_token(CLOSE_PAR);
      break;
    case PREDID:
      t = jj_consume_token(PREDID);
    p = new Predicado(t.image);
    c.addPredicado(p);
    System.out.print(t);
      jj_consume_token(OPEN_PAR);
    System.out.print("(");
      argumento(p);
    System.out.print(") ");
      jj_consume_token(CLOSE_PAR);
      break;
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void clausula() throws ParseException {
  Clausula c = new Clausula();
  clausulas.add(c);
    predicado(c);
    label_4:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case OPERADOR:
      case PREDID:
        ;
        break;
      default:
        jj_la1[4] = jj_gen;
        break label_4;
      }
      predicado(c);
    }
    jj_consume_token(13);
    System.out.print(".");
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case VARIABLE:
        ;
        break;
      default:
        jj_la1[5] = jj_gen;
        break label_5;
      }
      restricao(c);
    }
    System.out.println();
  }

  final public void declaracao() throws ParseException {
  Token t, t1, t2;
  String variable;
    t = jj_consume_token(VARIABLE);
    System.out.print(t + ":");
    jj_consume_token(14);
    t1 = jj_consume_token(NUMBER);
    System.out.print(t1 + " ");
    t2 = jj_consume_token(NUMBER);
    System.out.println(t2);
    jj_consume_token(13);
    Incrementador inc = new Incrementador(t.image, Integer.parseInt(t1.image), Integer.parseInt(t2.image));
    global.addToTable(t.image, inc);
  }

  final public void Start() throws ParseException {
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case VARIABLE:
        ;
        break;
      default:
        jj_la1[6] = jj_gen;
        break label_6;
      }
      declaracao();
    }
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case OPERADOR:
      case PREDID:
        ;
        break;
      default:
        jj_la1[7] = jj_gen;
        break label_7;
      }
      clausula();
    }
    jj_consume_token(0);
  }

  /** Generated Token Manager. */
  public TradutorTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[8];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x200,0x200,0x1000,0xa00,0xa00,0x40,0x40,0xa00,};
   }

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
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
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
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public Tradutor(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new TradutorTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public Tradutor(TradutorTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(TradutorTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 8; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
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

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[15];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 8; i++) {
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

}