package com.example.bfhl.service;

import com.example.bfhl.dto.BfhlRequest;
import com.example.bfhl.dto.BfhlResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BfhlServiceImpl implements BfhlService {

    @Value("${bfhl.user-id}")
    private String userId;

    @Value("${bfhl.email}")
    private String email;

    @Value("${bfhl.roll-number}")
    private String rollNumber;

    @Override
    public BfhlResponse processRequest(BfhlRequest request) {
        if (request == null || request.getData() == null) {
            return createErrorResponse();
        }

        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        List<Character> alphabetChars = new ArrayList<>();
        BigInteger sum = BigInteger.ZERO;

        for (String item : request.getData()) {
            if (item == null) {
                continue;
            }
            String trimmed = item.trim();
            if (trimmed.isEmpty()) {
                specialCharacters.add(item);
                continue;
            }

            // Check if it's an integer
            if (trimmed.matches("^-?\\d+$")) {
                try {
                    BigInteger val = new BigInteger(trimmed);
                    // Check if even or odd
                    if (!val.testBit(0)) {
                        evenNumbers.add(trimmed);
                    } else {
                        oddNumbers.add(trimmed);
                    }
                    sum = sum.add(val);
                } catch (NumberFormatException e) {
                    // Fallback to special characters if parsing fails
                    specialCharacters.add(trimmed);
                }
            } else if (trimmed.matches("^[a-zA-Z]+$")) {
                // Alphabet string
                alphabets.add(trimmed.toUpperCase());
                for (char c : trimmed.toCharArray()) {
                    alphabetChars.add(c);
                }
            } else {
                // Special characters or mixed
                specialCharacters.add(trimmed);
            }
        }

        // Generate concat_string
        String concatString = generateConcatString(alphabetChars);

        return new BfhlResponse(
                true,
                userId,
                email,
                rollNumber,
                oddNumbers,
                evenNumbers,
                alphabets,
                specialCharacters,
                sum.toString(),
                concatString
        );
    }

    private BfhlResponse createErrorResponse() {
        return new BfhlResponse(
                false,
                userId,
                email,
                rollNumber,
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                Collections.emptyList(),
                "0",
                ""
        );
    }

    private String generateConcatString(List<Character> chars) {
        if (chars == null || chars.isEmpty()) {
            return "";
        }
        
        // Reverse all characters
        List<Character> reversed = new ArrayList<>(chars);
        Collections.reverse(reversed);

        // Apply alternating caps starting with uppercase
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < reversed.size(); i++) {
            char c = reversed.get(i);
            if (i % 2 == 0) {
                sb.append(Character.toUpperCase(c));
            } else {
                sb.append(Character.toLowerCase(c));
            }
        }
        return sb.toString();
    }
}

