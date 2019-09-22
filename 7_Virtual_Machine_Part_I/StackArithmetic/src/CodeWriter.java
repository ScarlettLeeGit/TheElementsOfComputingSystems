import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CodeWriter {

    private BufferedWriter writer;
    private String filename;

    public CodeWriter(String filename) throws IOException {
        this.filename = filename.replace(".vm", "");
        writer = new BufferedWriter(new FileWriter(this.filename + ".asm"));
    }

    public void writeArithmetic(String command) throws IOException {
        StringBuilder assembly = new StringBuilder();

        switch (command) {
            case "add":
                /**
                 * second = *(SP -1);
                 * SP--;
                 * first = *(SP - 1);
                 * SP--;
                 * *SP = first + second;
                 * SP++;
                 */
                assembly.append("@SP").append("\n") // second
                        .append("D=M-1").append("\n")
                        .append("@second").append("\n")
                        .append("M=D").append("\n")
                        .append("@SP").append("\n") // SP--;
                        .append("M=M-1").append("\n")
                        .append("@SP").append("\n") // first
                        .append("D=M-1").append("\n")
                        .append("@SP").append("\n") // SP--;
                        .append("M=M-1").append("\n")
                        .append("@second").append("\n")
                        .append("A=M").append("\n")
                        .append("D=D+A").append("\n") // first + second. D still has the value of first
                        .append("@SP").append("\n")
                        .append("M=M+1").append("\n");

                break;
            case "sub":
                /**
                 * SP--;
                 * second = *SP;
                 * SP--;
                 * first = *SP;
                 * *SP = first - second;
                 * SP++;
                 */
                assembly.append("@SP").append("\n") // second
                        .append("D=M-1").append("\n")
                        .append("@second").append("\n")
                        .append("M=D").append("\n")
                        .append("@SP").append("\n") // SP--;
                        .append("M=M-1").append("\n")
                        .append("@SP").append("\n") // first
                        .append("D=M-1").append("\n")
                        .append("@SP").append("\n") // SP--;
                        .append("M=M-1").append("\n")
                        .append("@second").append("\n")
                        .append("A=M").append("\n")
                        .append("D=D-A").append("\n") // first - second. D still has the value of first
                        .append("@SP").append("\n")
                        .append("M=M+1").append("\n");
                break;
            case "neg":
                /**
                 * addr = SP - 1;
                 * *addr = (*addr) * (-1);
                 */
                assembly.append("@SP").append("\n")
                        .append("D=M-1").append("\n")
                        .append("@addr").append("\n")
                        .append("M=D").append("\n")
                        .append("@addr").append("\n")
                        .append("D=M").append("\n")
                        .append("M=-D").append("\n");
                break;
            case "eq":
                assembly.append("@SP").append("\n") // second
                        .append("D=M-1").append("\n")
                        .append("@second").append("\n")
                        .append("M=D").append("\n")
                        .append("@SP").append("\n") // SP--;
                        .append("M=M-1").append("\n")
                        .append("@SP").append("\n") // first
                        .append("D=M-1").append("\n")
                        .append("@SP").append("\n") // SP--;
                        .append("M=M-1").append("\n")
                        .append("@second").append("\n")
                        .append("D=D-M").append("\n") // first - second. D still has the value of first
                        .append("@EQUAL").append("\n")
                        .append("D;JEQ").append("\n")
                        .append("@SP").append("\n")
                        .append("M=0").append("\n")
                        .append("(EQUAL)").append("\n")
                        .append("   @SP").append("\n")
                        .append("   M=-1").append("\n")
                        .append("@SP").append("\n")
                        .append("M=M+1").append("\n");

                break;
            case "gt":
                assembly.append("@SP").append("\n") // second
                        .append("D=M-1").append("\n")
                        .append("@second").append("\n")
                        .append("M=D").append("\n")
                        .append("@SP").append("\n") // SP--;
                        .append("M=M-1").append("\n")
                        .append("@SP").append("\n") // first
                        .append("D=M-1").append("\n")
                        .append("@SP").append("\n") // SP--;
                        .append("M=M-1").append("\n")
                        .append("@second").append("\n")
                        .append("D=D-M").append("\n") // first - second. D still has the value of first
                        .append("@GREATER_THAN").append("\n")
                        .append("D;JGT").append("\n")
                        .append("@SP").append("\n")
                        .append("M=0").append("\n")
                        .append("(GREATER_THAN)").append("\n")
                        .append("   @SP").append("\n")
                        .append("   M=-1").append("\n")
                        .append("@SP").append("\n")
                        .append("M=M+1").append("\n");
                break;
            case "lt":
                assembly.append("@SP").append("\n") // second
                        .append("D=M-1").append("\n")
                        .append("@second").append("\n")
                        .append("M=D").append("\n")
                        .append("@SP").append("\n") // SP--;
                        .append("M=M-1").append("\n")
                        .append("@SP").append("\n") // first
                        .append("D=M-1").append("\n")
                        .append("@SP").append("\n") // SP--;
                        .append("M=M-1").append("\n")
                        .append("@second").append("\n")
                        .append("D=D-M").append("\n") // first - second. D still has the value of first
                        .append("@LESS_THAN").append("\n")
                        .append("D;JGT").append("\n")
                        .append("@SP").append("\n")
                        .append("M=0").append("\n")
                        .append("(LESS_THAN)").append("\n")
                        .append("   @SP").append("\n")
                        .append("   M=-1").append("\n")
                        .append("@SP").append("\n")
                        .append("M=M+1").append("\n");
                break;
            case "and":
                /**
                 * SP--;
                 * second = *SP;
                 * SP--;
                 * first = *SP;
                 * *SP = first & second;
                 * SP++;
                 */
                assembly.append("@SP").append("\n") // second
                        .append("D=M-1").append("\n")
                        .append("@second").append("\n")
                        .append("M=D").append("\n")
                        .append("@SP").append("\n") // SP--;
                        .append("M=M-1").append("\n")
                        .append("@SP").append("\n") // first
                        .append("D=M-1").append("\n")
                        .append("@SP").append("\n") // SP--;
                        .append("M=M-1").append("\n")
                        .append("@second").append("\n")
                        .append("A=M").append("\n")
                        .append("D=D&A").append("\n") // first & second. D still has the value of first
                        .append("@SP").append("\n")
                        .append("M=M+1").append("\n");
                break;
            case "or":
                /**
                 * SP--;
                 * second = *SP;
                 * SP--;
                 * first = *SP;
                 * *SP = first | second;
                 * SP++;
                 */
                assembly.append("@SP").append("\n") // second
                        .append("D=M-1").append("\n")
                        .append("@second").append("\n")
                        .append("M=D").append("\n")
                        .append("@SP").append("\n") // SP--;
                        .append("M=M-1").append("\n")
                        .append("@SP").append("\n") // first
                        .append("D=M-1").append("\n")
                        .append("@SP").append("\n") // SP--;
                        .append("M=M-1").append("\n")
                        .append("@second").append("\n")
                        .append("A=M").append("\n")
                        .append("D=D|A").append("\n") // first | second. D still has the value of first
                        .append("@SP").append("\n")
                        .append("M=M+1").append("\n");
                break;
            case "not":
                /**
                 * addr = SP - 1;
                 * *addr = !(*addr);
                 */
                assembly.append("@SP").append("\n")
                        .append("D=M-1").append("\n")
                        .append("@addr").append("\n")
                        .append("M=D").append("\n")
                        .append("@addr").append("\n")
                        .append("D=M").append("\n")
                        .append("M=!D").append("\n");
                break;
        }
        writer.write(assembly.toString());
        writer.flush();
    }

    public void writePushPop(CommandType command, String segment, int index) throws IOException {
        StringBuilder assembly = new StringBuilder();

        switch (command) {
            case C_POP:
                /**
                 * addr = segment + i
                 * SP--
                 * *addr = *SP
                 */
                switch (segment) {
                    case "local":
                        assembly.append("@").append(index).append("\n") // LCL + i
                                .append("D=A").append("\n")
                                .append("@LCL").append("\n")
                                .append("D=M+D").append("\n")
                                .append("@addr").append("\n") // addr = LCL + i
                                .append("M=D").append("\n")
                                .append("@SP").append("\n")
                                .append("MD=M-1").append("\n")
                                .append("@addr").append("\n")
                                .append("M=D").append("\n");
                        break;
                    case "argument":
                        assembly.append("@").append(index).append("\n") // LCL + i
                                .append("D=A").append("\n")
                                .append("@ARG").append("\n")
                                .append("D=M+D").append("\n")
                                .append("@addr").append("\n") // addr = LCL + i
                                .append("M=D").append("\n")
                                .append("@SP").append("\n")
                                .append("MD=M-1").append("\n")
                                .append("@addr").append("\n")
                                .append("M=D").append("\n");
                        break;
                    case "this":
                        assembly.append("@").append(index).append("\n") // LCL + i
                                .append("D=A").append("\n")
                                .append("@THIS").append("\n")
                                .append("D=M+D").append("\n")
                                .append("@addr").append("\n") // addr = LCL + i
                                .append("M=D").append("\n")
                                .append("@SP").append("\n")
                                .append("MD=M-1").append("\n")
                                .append("@addr").append("\n")
                                .append("M=D").append("\n");
                        break;
                    case "that":
                        assembly.append("@").append(index).append("\n") // LCL + i
                                .append("D=A").append("\n")
                                .append("@THAT").append("\n")
                                .append("D=M+D").append("\n")
                                .append("@addr").append("\n") // addr = LCL + i
                                .append("M=D").append("\n")
                                .append("@SP").append("\n")
                                .append("MD=M-1").append("\n")
                                .append("@addr").append("\n")
                                .append("M=D").append("\n");
                        break;
                    case "temp":
                        assembly.append("@").append(index).append("\n") // LCL + i
                                .append("D=A").append("\n")
                                .append("@R5").append("\n")
                                .append("D=M+D").append("\n")
                                .append("@addr").append("\n") // addr = LCL + i
                                .append("M=D").append("\n")
                                .append("@SP").append("\n")
                                .append("MD=M-1").append("\n")
                                .append("@addr").append("\n")
                                .append("M=D").append("\n");
                        break;
                    case "static":
                        assembly.append("@").append(filename).append(".").append(index).append("\n")
                                .append("D=A").append("\n")
                                .append("@addr").append("\n") // addr = LCL + i
                                .append("M=D").append("\n")
                                .append("@SP").append("\n")
                                .append("MD=M-1").append("\n")
                                .append("@addr").append("\n")
                                .append("M=D").append("\n");
                        break;
                    case "pointer":
                        assembly.append("@").append(index).append("\n") // LCL + i
                                .append("D=A").append("\n")
                                .append("@R3").append("\n")
                                .append("D=M+D").append("\n")
                                .append("@addr").append("\n") // addr = LCL + i
                                .append("M=D").append("\n")
                                .append("@SP").append("\n")
                                .append("MD=M-1").append("\n")
                                .append("@addr").append("\n")
                                .append("M=D").append("\n");
                        break;
                    case "constant":
                        assembly.append("@").append(index).append("\n") // LCL + i
                                .append("D=A").append("\n")
                                .append("@addr").append("\n") // addr = LCL + i
                                .append("M=D").append("\n")
                                .append("@SP").append("\n")
                                .append("MD=M-1").append("\n")
                                .append("@addr").append("\n")
                                .append("M=D").append("\n");
                        break;
                }
                break;

            case C_PUSH:
                switch (segment) {
                    case "local":
                        assembly.append("@").append(index).append("\n") // LCL + i
                                .append("D=A").append("\n")
                                .append("@LCL").append("\n")
                                .append("D=M+D").append("\n")
                                .append("@addr").append("\n") // addr = LCL + i
                                .append("M=D").append("\n")
                                .append("D=M").append("\n");

                        break;
                    case "argument":
                        assembly.append("@").append(index) // ARG + i
                                .append("D=A").append("\n")
                                .append("@ARG").append("\n")
                                .append("D=M+D").append("\n")
                                .append("@addr").append("\n") // addr = ARG + i
                                .append("M=D").append("\n")
                                .append("D=M").append("\n");
                        break;
                    case "this":
                        assembly.append("@").append(index) // THIS + i
                                .append("D=A").append("\n")
                                .append("@THIS").append("\n")
                                .append("D=M+D").append("\n")
                                .append("@addr").append("\n") // addr = THIS + i
                                .append("M=D").append("\n")
                                .append("D=M").append("\n");
                        break;
                    case "that":
                        assembly.append("@").append(index) // THAT + i
                                .append("D=A").append("\n")
                                .append("@THAT").append("\n")
                                .append("D=M+D").append("\n")
                                .append("@addr").append("\n") // addr = THAT + i
                                .append("M=D").append("\n")
                                .append("D=M").append("\n");
                        break;
                    case "temp":
                        assembly.append("@").append(index) // temp + i
                                .append("D=A").append("\n")
                                .append("@R5").append("\n")
                                .append("D=A+D").append("\n")
                                .append("@addr").append("\n") // addr = temp + i
                                .append("M=D").append("\n")
                                .append("D=M").append("\n");
                        break;
                    case "static":
                        assembly.append("@").append(filename).append(".").append(index).append("\n")
                                .append("D=A").append("\n");
                        break;
                    case "pointer":
                        assembly.append("@").append(index).append("\n") // pointer + i
                                .append("D=A").append("\n")
                                .append("@R3").append("\n")
                                .append("D=A+D").append("\n")
                                .append("@addr").append("\n") // addr = pointer + i
                                .append("M=D").append("\n")
                                .append("D=M").append("\n");
                        break;
                    case "constant":
                        assembly.append("@").append(index).append("\n") // D = i
                                .append("D=A").append("\n");
                        break;
                }
                assembly.append("@SP") // *SP = *addr
                    .append("A=M").append("\n")
                    .append("M=D").append("\n")
                    .append("@SP").append("\n") // SP++
                    .append("M=M+1").append("\n");
        }
        writer.write(assembly.toString());
        writer.flush();
    }

    public void close() throws IOException {
        writer.close();
    }
}
