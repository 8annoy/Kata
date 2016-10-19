package account;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by eitannoy on 10/19/16.
 */
public class StatementBuilder {
    private final LinkedList<AccountTransaction> transactions;
    private final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    private int dateWidth = 4, amountWidth = 6;

    public StatementBuilder(LinkedList<AccountTransaction> transactions) {
        this.transactions = transactions;
    }

    public String[] build() {
        LinkedList<String> rows = buildHeaders();
        rows.addAll(buildRows());
        return alignColumns(rows);
    }

    private String[] alignColumns(LinkedList<String> rows) {
        String[] result = new String[rows.size()];
        for(int i=0; i < rows.size(); i++) {
            result[i] = addPadding(rows.get(i));
        }
        return result;
    }

    private String addPadding(String row) {
        String[] parts = row.split("\\|");
        parts[0] = addPaddingTo_UpToLengthOf(parts[0], dateWidth);
        parts[1] = addPaddingTo_UpToLengthOf(parts[1], amountWidth);
        return String.join(" | ", parts);
    }

    private String addPaddingTo_UpToLengthOf(String part, int targetWidth) {
        String padding = "";
        for(int i = 0; i < targetWidth - part.length(); i++) {
            padding = padding.concat(" ");
        }
        return part.concat(padding);
    }

    private LinkedList<String> buildRows() {
        LinkedList<String> result = new LinkedList<>();
        for(AccountTransaction trx : transactions) {
            updateColumnWidths(trx);
            result.add(new StringBuilder(df.format(trx.getDate())).append("|").append(trx.getAmount()).append("|").append(trx.getBalance()).toString());
        }
        return result;
    }

    private void updateColumnWidths(AccountTransaction trx) {
        dateWidth = Math.max(dateWidth, df.format(trx.getDate()).length());
        amountWidth = Math.max(amountWidth, String.valueOf(trx.getAmount()).length());
    }

    private LinkedList<String> buildHeaders() {
        StringBuffer sb = new StringBuffer();
        for(StatementHeaders header : StatementHeaders.values()) {
            sb.append(toCamelCase(header)).append("|");
        }
        return new LinkedList<>(Arrays.asList(sb.deleteCharAt(sb.lastIndexOf("|")).toString()));
    }

    private String toCamelCase(StatementHeaders header) {
        return header.name().charAt(0) + header.name().substring(1).toLowerCase();
    }
}
