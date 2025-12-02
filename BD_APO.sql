-- ------------------- TABELAS -----------------------------------------------------------------------------------

create table usuario(
	codigo_usuario int auto_increment not null,
	cpf_usuario varchar(11),
	nome_usuario varchar(50),
    email_usuario varchar(40),
    telefone_usuario varchar(11),
    senha_usuario varchar(15),
    primary key (codigo_usuario, cpf_usuario, senha_usuario)
);  

-- Conta refere-se ao perfil do usuário no app
create table conta(
	cod_usuario int auto_increment not null,
	saldo_conta decimal(10,2),
    constraint fk_cod_usuario foreign key(cod_usuario) references usuario(codigo_usuario)
);

create table categoria_entrada(
	codigo_categoria int auto_increment primary key not null,
    nome_categoria varchar(30)
);

create table categoria_saida(
	codigo_categoria int auto_increment primary key not null,
    nome_categoria varchar(30)
);

create table entrada(
	codigo_entrada int auto_increment primary key,
    valor_entrada decimal(10,2),
    data_pagamento date,
    codigo_categoria_entrada int not null,
    codigo_usuario_entrada int not null,
    constraint fk_codigo_categoria_entrada foreign key(codigo_categoria_entrada) references categoria_entrada(codigo_categoria),
    constraint fk_codigo_usuario_entrada foreign key(codigo_usuario_entrada) references usuario(codigo_usuario)
);

create table saida(
	codigo_saida int auto_increment primary key,
    valor_saida decimal(10,2),
    data_vencimento date,
    codigo_categoria_saida int not null,
    codigo_usuario_saida int not null,
    tipo_pagamento enum('debito', 'credito')  not null default 'debito',
    codigo_cartao int,
    constraint fk_codigo_categoria_saida foreign key(codigo_categoria_saida) references categoria_saida(codigo_categoria),
    constraint fk_codigo_usuario_saida foreign key(codigo_usuario_saida) references usuario(codigo_usuario)
);

alter table saida add constraint chk_cartao_tipo
check (
    (tipo_pagamento = 'credito' and codigo_cartao is not null)
    or (tipo_pagamento = 'debito' and codigo_cartao is null)
);

create table movimentacoes(
	tipo enum('entrada', 'saida') not null,
    valor decimal(10,2),
    data_movimentacao date,
    cod_entrada int,
    cod_saida int,
    cod_usuario_mov int not null,
    cod_categoria int not null,
    constraint fk_cod_entrada foreign key(cod_entrada) references entrada(codigo_entrada),
    constraint fk_cod_saida foreign key(cod_saida) references saida(codigo_saida),
    constraint checar_um_ou_outro check (
        (cod_entrada is not null and cod_saida is null)
        OR (cod_saida is not null and cod_entrada is null)
    ),
    constraint fk_cod_usuario_mov foreign key(cod_usuario_mov) references usuario(codigo_usuario)
);
alter table movimentacoes
add column codigo_movimentacao int auto_increment primary key first;

create table Agenda (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL,
    dia INT NOT NULL,
    valor DECIMAL(10,2) NOT NULL,
    repetirMeses INT NOT NULL,
    usuarioId INT NOT NULL,
    categoriaId INT NOT NULL,
    data DATE
) COLLATE utf8mb4_bin;

create table AgendaEvento (
    Id INT AUTO_INCREMENT PRIMARY KEY,
    AgendaId INT NOT NULL,
    DataEvento DATE NOT NULL,
    Valor DOUBLE NOT NULL,
    Tipo VARCHAR(20) NOT NULL,
    UsuarioId INT NOT NULL,
    CategoriaId INT NOT NULL,

    foreign key (AgendaId) references Agenda(Id)
) COLLATE utf8mb4_bin;

-- ======================= TRIGGERS ==============================
DELIMITER $$
CREATE DEFINER=`root`@`localhost` trigger trg_entrada_movimentacao
after insert on entrada
for each row
BEGIN
    insert into movimentacoes(tipo, data_movimentacao, cod_entrada, cod_usuario_mov, valor, cod_categoria)
    values ('entrada', coalesce(new.data_pagamento, curdate()), new.codigo_entrada, new.codigo_usuario_entrada, new.valor_entrada, new.codigo_categoria_entrada);
END $$

DELIMITER ; 

DELIMITER $$
CREATE DEFINER=`root`@`localhost` trigger trg_saida_movimentacao
after insert on saida
for each row
BEGIN
    -- insere qualquer saída (débito ou crédito) na tabela movimentacoes
    insert into movimentacoes (tipo, data_movimentacao, cod_saida, cod_usuario_mov, cod_CategoriaSaida)
    values (
        'saida',
        case 
            when new.tipo_pagamento = 'debito' then curdate()  -- se for débito, usa a data atual
            else coalesce(new.data_vencimento, curdate())      -- se for crédito, usa a data de vencimento (ou a atual se estiver nula)
        end,
        new.codigo_saida,
        new.codigo_usuario_saida,
        new.codigo_categoria_saida
    );
END $$

DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` trigger trg_atualiza_saldo_saida
after insert on saida
for each row
begin
    if new.tipo_pagamento = 'debito' then
        update conta
        set saldo_conta = saldo_conta - new.valor_saida
        where cod_usuario = new.codigo_usuario_saida;
    end if;
end
$$ DELIMITER ;


DELIMITER $$
CREATE DEFINER=`root`@`localhost` trigger trg_atualiza_saldo_entrada
after insert on entrada
for each row
BEGIN
    update conta
    set saldo_conta = saldo_conta + new.valor_entrada
    where cod_usuario = new.codigo_usuario_entrada;
END
DELIMITER ; 


DELIMITER $$
CREATE DEFINER=`root`@`localhost` trigger trg_atualiza_cartao_credito
after insert on saida
for each row
BEGIN
    if new.tipo_pagamento = 'credito' then
        update cartao_credito
        set saldo_usado = saldo_usado + new.valor_saida
        where id_cartao = new.codigo_cartao;
    end if;
END $$
DELIMITER ;

-- ========================= PROCEDURES =================================
DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `inserir_usuario`(
    in p_cpf varchar(11),
    in p_nome varchar(50),
    in p_email varchar(40),
    in p_telefone varchar(11),
    in p_senha varchar(15)
)
BEGIN
insert into usuario(cpf_usuario, nome_usuario, email_usuario, telefone_usuario, senha_usuario)
values(p_cpf, p_nome, p_email, p_telefone, p_senha);
END $$
DELIMITER ;

alter table usuario auto_increment = 1; -- reseta auto_increment
call inserir_usuario('27612345769', 'Gabriel', 'gab@gmail.com', '987690543', 'a234fd4');
select * from usuario;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `inserir_conta`(
    in p_cod_usu int,
    in p_saldo decimal(10,2)
)
BEGIN
    insert into conta(cod_usuario, saldo_conta)
    values(p_cod_usu, p_saldo);
END
$$ DELIMITER ;
call inserir_conta(1, 0.00);
select * from conta; -- VERIFICAR SALDO



DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `inserir_categoria_entrada`(
    in p_nome varchar(30)
)
BEGIN
insert into categoria_entrada(nome_categoria)
values(p_nome);
END
$$ DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `inserir_categoria_saida`(
    in p_nome varchar(30)
)
BEGIN
insert into categoria_saida(nome_categoria)
values(p_nome);
END
$$ DELIMITER ;

-- LIMPAR ANTES DE INSERIR
set sql_safe_updates = 0;
delete from categoria_entrada;
alter table categoria_entrada auto_increment = 1;
select * from categoria_entrada;

set sql_safe_updates = 0;
delete from categoria_saida;
alter table categoria_saida auto_increment = 1;
select * from categoria_saida;



call inserir_categoria_entrada('Salário');
call inserir_categoria_entrada('Depósito Recebido');
call inserir_categoria_entrada('Auxílio');
call inserir_categoria_entrada('Outros');

call inserir_categoria_saida('Conta de água');
call inserir_categoria_saida('Conta de luz');
call inserir_categoria_saida('Internet');
call inserir_categoria_saida('Fast Food');
call inserir_categoria_saida('Uber');
call inserir_categoria_saida('Compras não previstas');
call inserir_categoria_saida('Aluguel');
call inserir_categoria_saida('Outros');


DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `inserir_entrada`(
    in p_valor decimal(10,2),
    in p_categoria int,
    in p_cod_usuario int 
)
BEGIN
    insert into entrada(valor_entrada, data_pagamento, codigo_categoria_entrada, codigo_usuario_entrada)
    values(p_valor, curdate(), p_categoria, p_cod_usuario);
END $$
DELIMITER ;
/* limpar movimentacoes e entradas antes da insercao
set sql_safe_updates = 0;
delete from movimentacoes;
alter table movimentacoes auto_increment = 1;
select * from movimentacoes;

set sql_safe_updates = 0;
delete from entrada;
alter table entrada auto_increment = 1;
select * from entrada;*/

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `GerarEventosAgenda`(IN pAgendaId INT)
BEGIN
    declare vDia INT;
    declare vValor DOUBLE;
    declare vTipo DECIMAL(10, 2);
    declare vUsuario INT;
    declare vCategoria INT;
    declare vRepetir INT;
    declare vData DATE;
    declare vCont INT default 1; -- começa em 1 para gerar EXATAMENTE vRepetir eventos
    declare vDataEvento DATE;
    declare vMesBase DATE;

    -- Carrega dados da agenda
    select Dia, Valor, Tipo, UsuarioId, CategoriaId, RepetirMeses, Data
    into vDia, vValor, vTipo, vUsuario, vCategoria, vRepetir, vData
    from Agenda where Id = pAgendaId;

    -- ignorando vData pois você não quer "data única"

    -- Ajuste: definir o mês inicial PARA O PRÓXIMO MÊS, caso o dia já tenha passado
    set vMesBase = curdate();

    if day(curdate()) > vDia then
        -- se o dia já passou, começa no mês seguinte
        set vMesBase = date_add(vMesBase, interval 1 month);
    end if;

    -- Gera EXATAMENTE "vRepetir" eventos FUTUROS
    while vCont <= vRepetir do

        -- gera data do evento baseado no mês corrigido
        set vDataEvento =
            date_add(
                last_day(date_add(vMesBase, interval (vCont - 1) month)),
                interval (vDia - day(last_day(date_add(vMesBase, interval (vCont - 1) month)))) day
            );

        insert into AgendaEvento (AgendaId, DataEvento, Valor, Tipo, UsuarioId, CategoriaId)
        values (pAgendaId, vDataEvento, vValor, vTipo, vUsuario, vCategoria);

        set vCont = vCont + 1;
    end while;

END
$$ DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `inserir_saida`(
    in p_valor decimal(10,2),
    in p_categoria int,
    in p_cod_usuario int,
    in p_tipo_pagamento enum('debito','credito'),
    in p_codigo_cartao int
)
BEGIN
    declare v_saldo_atual decimal(10,2);
    declare v_limite_restante decimal(10,2);
    declare v_dia_pagamento int;
    declare v_data_vencimento date;
    declare v_msg varchar(200);

    -- pega o saldo atual da conta
    select saldo_conta into v_saldo_atual
    from conta
    where cod_usuario = p_cod_usuario
    limit 1;

    -- se for débito, verifica se há saldo suficiente
    if p_tipo_pagamento = 'debito' then
        if v_saldo_atual < p_valor then
            set v_msg = concat('saldo insuficiente! você só tem R$ ', format(v_saldo_atual, 2));
            signal sqlstate '45000' set message_text = v_msg;
        end if;
    end if;

    -- se for crédito, valida o limite do cartão e define vencimento
    if p_tipo_pagamento = 'credito' then
        select (limite_total - saldo_usado), dia_pagamento
        into v_limite_restante, v_dia_pagamento
        from cartao_credito
        where id_cartao = p_codigo_cartao
        limit 1;

        if v_limite_restante < p_valor then
            set v_msg = concat('limite insuficiente! disponível: R$ ', format(v_limite_restante, 2));
            signal sqlstate '45000' set message_text = v_msg;
        end if;

        -- monta a data de vencimento da fatura (mesmo mês e ano atuais)
        set v_data_vencimento = str_to_date(
            concat(year(curdate()), '-', lpad(month(curdate()),2,'0'), '-', lpad(v_dia_pagamento,2,'0')),
            '%Y-%m-%d'
        );
    end if;

    -- insere a saída (triggers cuidarão do saldo e movimentações)
    insert into saida(
        valor_saida,
        data_vencimento,
        codigo_categoria_saida,
        codigo_usuario_saida,
        tipo_pagamento,
        codigo_cartao
    )
    values(
        p_valor,
        case when p_tipo_pagamento = 'credito' then v_data_vencimento else null end,
        p_categoria,
        p_cod_usuario,
        p_tipo_pagamento,
        p_codigo_cartao
    );

END
$$ DELIMITER ;

DELIMITER $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `inserir_cartao`(
    in p_nome_cartao varchar(50),
    in p_limite_total decimal(10,2),
    in p_saldo_usado decimal(10,2),
    in p_dia_fechamento int,
    in p_dia_pagamento int,
    in p_codigo_usuario int
)
BEGIN
    insert into cartao_credito(nome_cartao, limite_total, saldo_usado, dia_fechamento, dia_pagamento, codigo_usuario)
    values(p_nome_cartao, p_limite_total, p_saldo_usado, p_dia_fechamento, p_dia_pagamento, p_codigo_usuario);
END
$$ DELIMITER ;