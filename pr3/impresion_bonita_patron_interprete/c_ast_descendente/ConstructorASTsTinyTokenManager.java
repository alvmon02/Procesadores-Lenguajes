/* ConstructorASTsTinyTokenManager.java */
/* Generated By:JavaCC: Do not edit this line. ConstructorASTsTinyTokenManager.java */
package c_ast_descendente;
import asint2.SintaxisAbstractaTiny.*;
import asint2.*;

/** Token Manager. */
@SuppressWarnings ("unused")
public class ConstructorASTsTinyTokenManager implements ConstructorASTsTinyConstants {

  /** Debug output. */
  public  java.io.PrintStream debugStream = System.out;
  /** Set debug output. */
  public  void setDebugStream(java.io.PrintStream ds) { debugStream = ds; }
private final int jjStopStringLiteralDfa_0(int pos, long active0){
   switch (pos)
   {
      case 0:
         if ((active0 & 0xc0000000000000L) != 0L)
            return 89;
         return -1;
      default :
         return -1;
   }
}
private final int jjStartNfa_0(int pos, long active0){
   return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
}
private int jjStopAtPos(int pos, int kind)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   return pos + 1;
}
private int jjMoveStringLiteralDfa0_0(){
   switch(curChar)
   {
      case 33:
         return jjMoveStringLiteralDfa1_0(0x20000000000000L);
      case 37:
         return jjStopAtPos(0, 58);
      case 38:
         jjmatchedKind = 42;
         return jjMoveStringLiteralDfa1_0(0x2000000000L);
      case 40:
         return jjStopAtPos(0, 39);
      case 41:
         return jjStopAtPos(0, 40);
      case 42:
         return jjStopAtPos(0, 56);
      case 43:
         return jjStartNfaWithStates_0(0, 55, 89);
      case 44:
         return jjStopAtPos(0, 41);
      case 45:
         return jjStartNfaWithStates_0(0, 54, 89);
      case 46:
         return jjStopAtPos(0, 59);
      case 47:
         return jjStopAtPos(0, 57);
      case 59:
         return jjStopAtPos(0, 38);
      case 60:
         jjmatchedKind = 49;
         return jjMoveStringLiteralDfa1_0(0x10000000000000L);
      case 61:
         jjmatchedKind = 47;
         return jjMoveStringLiteralDfa1_0(0x1000000000000L);
      case 62:
         jjmatchedKind = 50;
         return jjMoveStringLiteralDfa1_0(0x8000000000000L);
      case 64:
         return jjStopAtPos(0, 46);
      case 91:
         return jjStopAtPos(0, 43);
      case 93:
         return jjStopAtPos(0, 44);
      case 94:
         return jjStopAtPos(0, 45);
      case 123:
         return jjStopAtPos(0, 35);
      case 125:
         return jjStopAtPos(0, 36);
      default :
         return jjMoveNfa_0(0, 0);
   }
}
private int jjMoveStringLiteralDfa1_0(long active0){
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) {
      jjStopStringLiteralDfa_0(0, active0);
      return 1;
   }
   switch(curChar)
   {
      case 38:
         if ((active0 & 0x2000000000L) != 0L)
            return jjStopAtPos(1, 37);
         break;
      case 61:
         if ((active0 & 0x1000000000000L) != 0L)
            return jjStopAtPos(1, 48);
         else if ((active0 & 0x8000000000000L) != 0L)
            return jjStopAtPos(1, 51);
         else if ((active0 & 0x10000000000000L) != 0L)
            return jjStopAtPos(1, 52);
         else if ((active0 & 0x20000000000000L) != 0L)
            return jjStopAtPos(1, 53);
         break;
      default :
         break;
   }
   return jjStartNfa_0(0, active0);
}
private int jjStartNfaWithStates_0(int pos, int kind, int state)
{
   jjmatchedKind = kind;
   jjmatchedPos = pos;
   try { curChar = input_stream.readChar(); }
   catch(java.io.IOException e) { return pos + 1; }
   return jjMoveNfa_0(state, pos + 1);
}
static final long[] jjbitVec0 = {
   0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL
};
private int jjMoveNfa_0(int startState, int curPos)
{
   int startsAt = 0;
   jjnewStateCnt = 110;
   int i = 1;
   jjstateSet[0] = startState;
   int kind = 0x7fffffff;
   for (;;)
   {
      if (++jjround == 0x7fffffff)
         ReInitRounds();
      if (curChar < 64)
      {
         long l = 1L << curChar;
         do
         {
            switch(jjstateSet[--i])
            {
               case 89:
                  if ((0x3fe000000000000L & l) != 0L)
                     { jjCheckNAddStates(0, 2); }
                  else if (curChar == 48)
                     { jjCheckNAddTwoStates(94, 98); }
                  if ((0x3fe000000000000L & l) != 0L)
                  {
                     if (kind > 32)
                        kind = 32;
                     { jjCheckNAdd(90); }
                  }
                  else if (curChar == 48)
                  {
                     if (kind > 32)
                        kind = 32;
                  }
                  break;
               case 0:
                  if ((0x3fe000000000000L & l) != 0L)
                  {
                     if (kind > 32)
                        kind = 32;
                     { jjCheckNAddStates(3, 6); }
                  }
                  else if ((0x100002700L & l) != 0L)
                  {
                     if (kind > 7)
                        kind = 7;
                  }
                  else if ((0x280000000000L & l) != 0L)
                     { jjAddStates(7, 10); }
                  else if (curChar == 48)
                  {
                     if (kind > 32)
                        kind = 32;
                     { jjCheckNAddTwoStates(94, 98); }
                  }
                  else if (curChar == 34)
                     { jjCheckNAddTwoStates(39, 40); }
                  else if (curChar == 35)
                     jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 1:
                  if (curChar != 35)
                     break;
                  if (kind > 8)
                     kind = 8;
                  { jjCheckNAdd(2); }
                  break;
               case 2:
                  if ((0xfffffffffffffbffL & l) == 0L)
                     break;
                  if (kind > 8)
                     kind = 8;
                  { jjCheckNAdd(2); }
                  break;
               case 3:
                  if (curChar == 35)
                     jjstateSet[jjnewStateCnt++] = 1;
                  break;
               case 37:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 31)
                     kind = 31;
                  jjstateSet[jjnewStateCnt++] = 37;
                  break;
               case 38:
                  if (curChar == 34)
                     { jjCheckNAddTwoStates(39, 40); }
                  break;
               case 39:
                  if ((0xfffffffbffffffffL & l) != 0L)
                     { jjCheckNAddTwoStates(39, 40); }
                  break;
               case 40:
                  if (curChar == 34 && kind > 34)
                     kind = 34;
                  break;
               case 88:
                  if ((0x280000000000L & l) != 0L)
                     { jjAddStates(7, 10); }
                  break;
               case 90:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 32)
                     kind = 32;
                  { jjCheckNAdd(90); }
                  break;
               case 91:
                  if (curChar == 48 && kind > 32)
                     kind = 32;
                  break;
               case 92:
                  if ((0x3fe000000000000L & l) != 0L)
                     { jjCheckNAddStates(0, 2); }
                  break;
               case 93:
                  if ((0x3ff000000000000L & l) != 0L)
                     { jjCheckNAddStates(0, 2); }
                  break;
               case 95:
                  if ((0x3fe000000000000L & l) == 0L)
                     break;
                  if (kind > 33)
                     kind = 33;
                  { jjCheckNAdd(96); }
                  break;
               case 96:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 33)
                     kind = 33;
                  { jjCheckNAdd(96); }
                  break;
               case 97:
                  if (curChar == 48 && kind > 33)
                     kind = 33;
                  break;
               case 98:
                  if (curChar == 46)
                     { jjCheckNAddStates(11, 16); }
                  break;
               case 99:
                  if ((0x3ff000000000000L & l) != 0L)
                     { jjCheckNAddTwoStates(99, 100); }
                  break;
               case 100:
                  if ((0x3fe000000000000L & l) != 0L && kind > 33)
                     kind = 33;
                  break;
               case 101:
                  if ((0x3ff000000000000L & l) != 0L)
                     { jjCheckNAddTwoStates(101, 102); }
                  break;
               case 102:
                  if ((0x3fe000000000000L & l) != 0L)
                     { jjCheckNAdd(103); }
                  break;
               case 104:
                  if ((0x3fe000000000000L & l) == 0L)
                     break;
                  if (kind > 33)
                     kind = 33;
                  { jjCheckNAdd(105); }
                  break;
               case 105:
                  if ((0x3ff000000000000L & l) == 0L)
                     break;
                  if (kind > 33)
                     kind = 33;
                  { jjCheckNAdd(105); }
                  break;
               case 106:
                  if (curChar == 48)
                     { jjCheckNAdd(103); }
                  break;
               case 107:
                  if (curChar == 48)
                     { jjCheckNAddTwoStates(94, 98); }
                  break;
               case 108:
                  if ((0x3fe000000000000L & l) == 0L)
                     break;
                  if (kind > 32)
                     kind = 32;
                  { jjCheckNAddStates(3, 6); }
                  break;
               case 109:
                  if (curChar != 48)
                     break;
                  if (kind > 32)
                     kind = 32;
                  { jjCheckNAddTwoStates(94, 98); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else if (curChar < 128)
      {
         long l = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 0:
                  if ((0x7fffffe07fffffeL & l) != 0L)
                  {
                     if (kind > 31)
                        kind = 31;
                     { jjCheckNAdd(37); }
                  }
                  if ((0x80000000800000L & l) != 0L)
                     { jjAddStates(17, 18); }
                  else if ((0x10000000100000L & l) != 0L)
                     { jjAddStates(19, 20); }
                  else if ((0x8000000080000L & l) != 0L)
                     { jjAddStates(21, 22); }
                  else if ((0x400000004000L & l) != 0L)
                     { jjAddStates(23, 26); }
                  else if ((0x4000000040000L & l) != 0L)
                     { jjAddStates(27, 28); }
                  else if ((0x20000000200L & l) != 0L)
                     { jjAddStates(29, 30); }
                  else if ((0x800000008L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 33;
                  else if ((0x1000000010L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 27;
                  else if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 23;
                  else if ((0x1000000010000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 19;
                  else if ((0x4000000040L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 14;
                  else if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 12;
                  else if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 9;
                  else if ((0x400000004L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 2:
                  if (kind > 8)
                     kind = 8;
                  jjstateSet[jjnewStateCnt++] = 2;
                  break;
               case 4:
                  if ((0x400000004L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 5;
                  break;
               case 5:
                  if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 6;
                  break;
               case 6:
                  if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 7;
                  break;
               case 7:
                  if ((0x100000001000L & l) != 0L && kind > 11)
                     kind = 11;
                  break;
               case 8:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 9;
                  break;
               case 9:
                  if ((0x400000004000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 10;
                  break;
               case 10:
                  if ((0x1000000010L & l) != 0L && kind > 12)
                     kind = 12;
                  break;
               case 11:
                  if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 12;
                  break;
               case 12:
                  if ((0x4000000040000L & l) != 0L && kind > 13)
                     kind = 13;
                  break;
               case 13:
                  if ((0x4000000040L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 14;
                  break;
               case 14:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 15;
                  break;
               case 15:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 16;
                  break;
               case 16:
                  if ((0x8000000080000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 17;
                  break;
               case 17:
                  if ((0x2000000020L & l) != 0L && kind > 18)
                     kind = 18;
                  break;
               case 18:
                  if ((0x1000000010000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 19;
                  break;
               case 19:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 20;
                  break;
               case 20:
                  if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 21;
                  break;
               case 21:
                  if ((0x800000008L & l) != 0L && kind > 19)
                     kind = 19;
                  break;
               case 22:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 23;
                  break;
               case 23:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 24;
                  break;
               case 24:
                  if ((0x8000000080000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 25;
                  break;
               case 25:
                  if ((0x2000000020L & l) != 0L && kind > 21)
                     kind = 21;
                  break;
               case 26:
                  if ((0x1000000010L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 27;
                  break;
               case 27:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 28;
                  break;
               case 28:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 29;
                  break;
               case 29:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 30;
                  break;
               case 30:
                  if ((0x10000000100000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 31;
                  break;
               case 31:
                  if ((0x2000000020L & l) != 0L && kind > 25)
                     kind = 25;
                  break;
               case 32:
                  if ((0x800000008L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 33;
                  break;
               case 33:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 34;
                  break;
               case 34:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 35;
                  break;
               case 35:
                  if ((0x100000001000L & l) != 0L && kind > 30)
                     kind = 30;
                  break;
               case 36:
                  if ((0x7fffffe07fffffeL & l) == 0L)
                     break;
                  if (kind > 31)
                     kind = 31;
                  { jjCheckNAdd(37); }
                  break;
               case 37:
                  if ((0x7fffffe87fffffeL & l) == 0L)
                     break;
                  if (kind > 31)
                     kind = 31;
                  { jjCheckNAdd(37); }
                  break;
               case 39:
                  { jjAddStates(31, 32); }
                  break;
               case 41:
                  if ((0x20000000200L & l) != 0L)
                     { jjAddStates(29, 30); }
                  break;
               case 42:
                  if ((0x400000004000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 43;
                  break;
               case 43:
                  if ((0x10000000100000L & l) != 0L && kind > 9)
                     kind = 9;
                  break;
               case 44:
                  if ((0x4000000040L & l) != 0L && kind > 20)
                     kind = 20;
                  break;
               case 45:
                  if ((0x4000000040000L & l) != 0L)
                     { jjAddStates(27, 28); }
                  break;
               case 46:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 47;
                  break;
               case 47:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 48;
                  break;
               case 48:
                  if ((0x100000001000L & l) != 0L && kind > 10)
                     kind = 10;
                  break;
               case 49:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 50;
                  break;
               case 50:
                  if ((0x200000002L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 51;
                  break;
               case 51:
                  if ((0x1000000010L & l) != 0L && kind > 26)
                     kind = 26;
                  break;
               case 52:
                  if ((0x400000004000L & l) != 0L)
                     { jjAddStates(23, 26); }
                  break;
               case 53:
                  if ((0x800000008000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 54;
                  break;
               case 54:
                  if ((0x10000000100000L & l) != 0L && kind > 14)
                     kind = 14;
                  break;
               case 55:
                  if ((0x20000000200000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 56;
                  break;
               case 56:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 57;
                  break;
               case 57:
                  if ((0x100000001000L & l) != 0L && kind > 16)
                     kind = 16;
                  break;
               case 58:
                  if ((0x2000000020L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 59;
                  break;
               case 59:
                  if ((0x80000000800000L & l) != 0L && kind > 24)
                     kind = 24;
                  break;
               case 60:
                  if ((0x100000001000L & l) != 0L && kind > 28)
                     kind = 28;
                  break;
               case 61:
                  if ((0x8000000080000L & l) != 0L)
                     { jjAddStates(21, 22); }
                  break;
               case 62:
                  if ((0x10000000100000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 63;
                  break;
               case 63:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 64;
                  break;
               case 64:
                  if ((0x20000000200L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 65;
                  break;
               case 65:
                  if ((0x400000004000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 66;
                  break;
               case 66:
                  if ((0x8000000080L & l) != 0L && kind > 15)
                     kind = 15;
                  break;
               case 67:
                  if ((0x10000000100000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 68;
                  break;
               case 68:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 69;
                  break;
               case 69:
                  if ((0x20000000200000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 70;
                  break;
               case 70:
                  if ((0x800000008L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 71;
                  break;
               case 71:
                  if ((0x10000000100000L & l) != 0L && kind > 23)
                     kind = 23;
                  break;
               case 72:
                  if ((0x10000000100000L & l) != 0L)
                     { jjAddStates(19, 20); }
                  break;
               case 73:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 74;
                  break;
               case 74:
                  if ((0x20000000200000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 75;
                  break;
               case 75:
                  if ((0x2000000020L & l) != 0L && kind > 17)
                     kind = 17;
                  break;
               case 76:
                  if ((0x200000002000000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 77;
                  break;
               case 77:
                  if ((0x1000000010000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 78;
                  break;
               case 78:
                  if ((0x2000000020L & l) != 0L && kind > 29)
                     kind = 29;
                  break;
               case 79:
                  if ((0x80000000800000L & l) != 0L)
                     { jjAddStates(17, 18); }
                  break;
               case 80:
                  if ((0x10000000100L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 81;
                  break;
               case 81:
                  if ((0x20000000200L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 82;
                  break;
               case 82:
                  if ((0x100000001000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 83;
                  break;
               case 83:
                  if ((0x2000000020L & l) != 0L && kind > 22)
                     kind = 22;
                  break;
               case 84:
                  if ((0x4000000040000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 85;
                  break;
               case 85:
                  if ((0x20000000200L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 86;
                  break;
               case 86:
                  if ((0x10000000100000L & l) != 0L)
                     jjstateSet[jjnewStateCnt++] = 87;
                  break;
               case 87:
                  if ((0x2000000020L & l) != 0L && kind > 27)
                     kind = 27;
                  break;
               case 94:
                  if ((0x2000000020L & l) != 0L)
                     { jjCheckNAddTwoStates(95, 97); }
                  break;
               case 103:
                  if ((0x2000000020L & l) != 0L)
                     { jjCheckNAddTwoStates(104, 97); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      else
      {
         int i2 = (curChar & 0xff) >> 6;
         long l2 = 1L << (curChar & 077);
         do
         {
            switch(jjstateSet[--i])
            {
               case 2:
                  if ((jjbitVec0[i2] & l2) == 0L)
                     break;
                  if (kind > 8)
                     kind = 8;
                  jjstateSet[jjnewStateCnt++] = 2;
                  break;
               case 39:
                  if ((jjbitVec0[i2] & l2) != 0L)
                     { jjAddStates(31, 32); }
                  break;
               default : break;
            }
         } while(i != startsAt);
      }
      if (kind != 0x7fffffff)
      {
         jjmatchedKind = kind;
         jjmatchedPos = curPos;
         kind = 0x7fffffff;
      }
      ++curPos;
      if ((i = jjnewStateCnt) == (startsAt = 110 - (jjnewStateCnt = startsAt)))
         return curPos;
      try { curChar = input_stream.readChar(); }
      catch(java.io.IOException e) { return curPos; }
   }
}

/** Token literal values. */
public static final String[] jjstrLiteralImages = {
"", null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, null, null, null, null, null, null, 
null, null, null, null, null, null, null, null, "\173", "\175", "\46\46", "\73", 
"\50", "\51", "\54", "\46", "\133", "\135", "\136", "\100", "\75", "\75\75", "\74", 
"\76", "\76\75", "\74\75", "\41\75", "\55", "\53", "\52", "\57", "\45", "\56", };
protected Token jjFillToken()
{
   final Token t;
   final String curTokenImage;
   final int beginLine;
   final int endLine;
   final int beginColumn;
   final int endColumn;
   String im = jjstrLiteralImages[jjmatchedKind];
   curTokenImage = (im == null) ? input_stream.GetImage() : im;
   beginLine = input_stream.getBeginLine();
   beginColumn = input_stream.getBeginColumn();
   endLine = input_stream.getEndLine();
   endColumn = input_stream.getEndColumn();
   t = Token.newToken(jjmatchedKind, curTokenImage);

   t.beginLine = beginLine;
   t.endLine = endLine;
   t.beginColumn = beginColumn;
   t.endColumn = endColumn;

   return t;
}
static final int[] jjnextStates = {
   93, 94, 98, 90, 93, 94, 98, 89, 91, 92, 107, 99, 100, 97, 101, 102, 
   106, 80, 84, 73, 76, 62, 67, 53, 55, 58, 60, 46, 49, 42, 44, 39, 
   40, 
};

int curLexState = 0;
int defaultLexState = 0;
int jjnewStateCnt;
int jjround;
int jjmatchedPos;
int jjmatchedKind;

/** Get the next Token. */
public Token getNextToken() 
{
  Token matchedToken;
  int curPos = 0;

  EOFLoop :
  for (;;)
  {
   try
   {
      curChar = input_stream.BeginToken();
   }
   catch(Exception e)
   {
      jjmatchedKind = 0;
      jjmatchedPos = -1;
      matchedToken = jjFillToken();
      return matchedToken;
   }

   jjmatchedKind = 0x7fffffff;
   jjmatchedPos = 0;
   curPos = jjMoveStringLiteralDfa0_0();
   if (jjmatchedKind != 0x7fffffff)
   {
      if (jjmatchedPos + 1 < curPos)
         input_stream.backup(curPos - jjmatchedPos - 1);
      if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L)
      {
         matchedToken = jjFillToken();
         return matchedToken;
      }
      else
      {
         continue EOFLoop;
      }
   }
   int error_line = input_stream.getEndLine();
   int error_column = input_stream.getEndColumn();
   String error_after = null;
   boolean EOFSeen = false;
   try { input_stream.readChar(); input_stream.backup(1); }
   catch (java.io.IOException e1) {
      EOFSeen = true;
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
      if (curChar == '\n' || curChar == '\r') {
         error_line++;
         error_column = 0;
      }
      else
         error_column++;
   }
   if (!EOFSeen) {
      input_stream.backup(1);
      error_after = curPos <= 1 ? "" : input_stream.GetImage();
   }
   throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenMgrError.LEXICAL_ERROR);
  }
}

void SkipLexicalActions(Token matchedToken)
{
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
void MoreLexicalActions()
{
   jjimageLen += (lengthOfMatch = jjmatchedPos + 1);
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
void TokenLexicalActions(Token matchedToken)
{
   switch(jjmatchedKind)
   {
      default :
         break;
   }
}
private void jjCheckNAdd(int state)
{
   if (jjrounds[state] != jjround)
   {
      jjstateSet[jjnewStateCnt++] = state;
      jjrounds[state] = jjround;
   }
}
private void jjAddStates(int start, int end)
{
   do {
      jjstateSet[jjnewStateCnt++] = jjnextStates[start];
   } while (start++ != end);
}
private void jjCheckNAddTwoStates(int state1, int state2)
{
   jjCheckNAdd(state1);
   jjCheckNAdd(state2);
}

private void jjCheckNAddStates(int start, int end)
{
   do {
      jjCheckNAdd(jjnextStates[start]);
   } while (start++ != end);
}

    /** Constructor. */
    public ConstructorASTsTinyTokenManager(SimpleCharStream stream){

      if (SimpleCharStream.staticFlag)
            throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");

    input_stream = stream;
  }

  /** Constructor. */
  public ConstructorASTsTinyTokenManager (SimpleCharStream stream, int lexState){
    ReInit(stream);
    SwitchTo(lexState);
  }

  /** Reinitialise parser. */
  
  public void ReInit(SimpleCharStream stream)
  {


    jjmatchedPos =
    jjnewStateCnt =
    0;
    curLexState = defaultLexState;
    input_stream = stream;
    ReInitRounds();
  }

  private void ReInitRounds()
  {
    int i;
    jjround = 0x80000001;
    for (i = 110; i-- > 0;)
      jjrounds[i] = 0x80000000;
  }

  /** Reinitialise parser. */
  public void ReInit(SimpleCharStream stream, int lexState)
  
  {
    ReInit(stream);
    SwitchTo(lexState);
  }

  /** Switch to specified lex state. */
  public void SwitchTo(int lexState)
  {
    if (lexState >= 1 || lexState < 0)
      throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenMgrError.INVALID_LEXICAL_STATE);
    else
      curLexState = lexState;
  }


/** Lexer state names. */
public static final String[] lexStateNames = {
   "DEFAULT",
};

/** Lex State array. */
public static final int[] jjnewLexState = {
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
   -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
};
static final long[] jjtoToken = {
   0xffffffffffffe01L, 
};
static final long[] jjtoSkip = {
   0x180L, 
};
static final long[] jjtoSpecial = {
   0x0L, 
};
static final long[] jjtoMore = {
   0x0L, 
};
    protected SimpleCharStream  input_stream;

    private final int[] jjrounds = new int[110];
    private final int[] jjstateSet = new int[2 * 110];
    private final StringBuilder jjimage = new StringBuilder();
    private StringBuilder image = jjimage;
    private int jjimageLen;
    private int lengthOfMatch;
    protected int curChar;
}
