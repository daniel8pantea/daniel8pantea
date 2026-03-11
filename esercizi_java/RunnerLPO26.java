package it.univaq.disim.hello;

import java.time.Duration;
import java.time.Instant;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class RunnerLPO26 {

    /**
     * Crea le opzioni da linea di comando:
     * - una sola modalità tra --primo e --goldbach
     * - un valore intero con --value
     * - --help per mostrare l'aiuto
     */
    private static Options buildOptions() {
        Options ops = new Options();

        Option help = Option.builder("h")
                .longOpt("help")
                .desc("Stampa il messaggio di aiuto")
                .build();

        Option value = Option.builder("v")
                .longOpt("value")
                .hasArg()
                .argName("numero")
                .desc("Valore intero da verificare")
                .build();

        Option primeMode = Option.builder("p")
                .longOpt("primo")
                .desc("Calcola se il valore è primo")
                .build();

        Option goldbachMode = Option.builder("g")
                .longOpt("goldbach")
                .desc("Verifica la congettura di Goldbach sul valore")
                .build();

        OptionGroup modeGroup = new OptionGroup();
        modeGroup.addOption(primeMode);
        modeGroup.addOption(goldbachMode);
        modeGroup.setRequired(false); // lasciamo false per permettere --help da solo

        ops.addOption(help);
        ops.addOption(value);
        ops.addOptionGroup(modeGroup);

        return ops;
    }

    /**
     * Stampa il messaggio di aiuto con le opzioni disponibili e alcuni esempi di utilizzo. 
     * @param ops le opzioni da mostrare nell'aiuto
     */
    private static void printHelp(Options ops) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(
                "java it.univaq.disim.hello.RunnerLPO26 [opzioni]",
                "\nEsempi:\n  --primo --value 17\n  --goldbach --value 100000\n",
                ops,
                "");
    }

    /**
     * Valida gli argomenti passati da linea di comando, assicurandosi che:
     * @param line la linea di comando da validare
     * @throws ParseException  se mancano argomenti obbligatori o se ci sono combinazioni di opzioni non valide
     */
    private static void validate(CommandLine line) throws ParseException {
        if (!line.hasOption("value")) {
            throw new ParseException("Manca --value <numero>.");
        }
        if (!line.hasOption("primo") && !line.hasOption("goldbach")) {
            throw new ParseException("Devi scegliere una modalità: --primo oppure --goldbach.");
        }
    }

    public static void main(String[] args) {
        System.out.println("[STEP 1] Definizione opzioni...");
        Options ops = buildOptions();

        CommandLineParser parser = new DefaultParser();
        try {
            System.out.println("[STEP 2] Parsing argomenti...");
            CommandLine line = parser.parse(ops, args);

            if (line.hasOption("help")) {
                System.out.println("[STEP 3] Richiesto help.");
                printHelp(ops);
                return;
            }

            System.out.println("[STEP 3] Validazione argomenti...");
            validate(line);

            int numeroDaVerificare;
            try {
                numeroDaVerificare = Integer.parseInt(line.getOptionValue("value"));
            } catch (NumberFormatException ex) {
                System.out.println("Errore: --value deve essere un intero valido.");
                return;
            }

            System.out.println("[STEP 4] Esecuzione logica applicativa...");

            if (line.hasOption("primo")) {
                boolean primo = SupportoLPONumeri26.isPrime(numeroDaVerificare);
                System.out.printf("Il numero %d %s primo.%n", numeroDaVerificare, primo ? "è" : "non è");
            }

            if (line.hasOption("goldbach")) {
                Instant inizio = Instant.now();
                boolean verifica = SupportoLPONumeri26.goldbach(numeroDaVerificare);
                System.out.printf("Il numero %d %s la congettura di Goldbach.%n",
                        numeroDaVerificare, verifica ? "verifica" : "non verifica");
                Duration elapsed = Duration.between(inizio, Instant.now());
                System.out.printf("Tempo trascorso: %d millisecondi%n", elapsed.toMillis());
            }

            System.out.println("[STEP 5] Fine.");

        } catch (ParseException e) {
            System.out.println("Errore nei parametri: " + e.getMessage());
            printHelp(ops);
        }
    }
}
