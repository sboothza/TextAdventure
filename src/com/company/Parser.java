package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private final BufferedReader _reader;
    private final String _prompt;
    private final ListMap<String, IWordEvent> _listeners;
    private final List<Tuple<String>> _overrides;
    private IWordEvent _catchAllListener;

    public Parser(String prompt) {
        _listeners = new ListMap<>();
        _reader = new BufferedReader(new InputStreamReader(System.in));
        _prompt = prompt;
        _overrides = new ArrayList<>();
    }

    public void addListener(String word, IWordEvent listener, boolean isCatchAll) {
        if (!isCatchAll)
            _listeners.put(word, listener);
        else
            _catchAllListener = listener;
    }

    public void addListeners(String[] words, IWordEvent listener) {
        for (String word : words)
            _listeners.put(word, listener);
    }

    public void process() throws IOException {
        System.out.print(_prompt);
        String input = _reader.readLine();
        System.out.println();
        for (Tuple<String> replace : _overrides) {
            input = input.replace(replace.x, replace.y);
        }

        String[] words = input.split("\\s");
        for (IWordEvent event : _listeners.get(words[0])) {
            event.Say(words[0], words);
        }

        if (!_listeners.containsKey(words[0]))
            _catchAllListener.Say(words[0], words);
    }

    public void addOverride(String replace, String with) {
        _overrides.add(new Tuple<>(replace, with));
    }
}
