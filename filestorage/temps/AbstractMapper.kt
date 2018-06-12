package templates.temps

abstract class AbstractMapper<FROM, TO> {
    abstract fun parse(from: FROM): TO
}