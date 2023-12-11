#!/usr/bin/env swipl

:- initialization(main,main).
:- use_module(library(socket)).
:- use_module(library(random)).

main:-
    sleep(15),
    client(localhost,3333).

client(Host,Port):-
    setup_call_cleanup(
    tcp_connect(Host:Port, Stream,[]),
    bot(Stream),
    close(Stream)).
bot(Stream):-
    %atom_codes(Atom,String),
    %flush_output(Stream),
    %copy_stream_data(Stream,current_output),
    format(Stream,'~s~n',"ai_boris2"),
    flush_output(Stream),
    %copy_stream_data(Stream,current_output),
    %read_stream(Stream,List),    %write(List),
    repeat,(
    (   maybe(1,2)->(
    maybe(1,2)->format(Stream,'~s~n',"north");format(Stream,'~s~n',"south"))
    ;(maybe(1,2)->format(Stream,'~s~n',"east");format(Stream,'~s~n',"west"))),
        flush_output(Stream),
        sleep(10),
        format(Stream,'~s~n',"yell NINININININININI"),
        flush_output(Stream),fail
    ).
read_stream(Stream,[H|T]):-
    \+(at_end_of_stream(Stream))->
    (   read_line_to_string(Stream,H),
    read_stream(Stream,T)
    )
    ;
    T = [].

