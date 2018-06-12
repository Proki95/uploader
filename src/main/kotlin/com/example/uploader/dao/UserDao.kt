package com.example.uploader.dao


import javax.persistence.*

@Entity
@Table(name="usersx")
class UserDao {
    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @Column(name="login",nullable = false)
    var login: String? = null

    @Column(name="password",nullable = false)
    var password: String? = null

    @Column(name="email",nullable = false)
    var email: String? = null

    @ManyToMany()
    @JoinTable(
            name = "rank_user",
            joinColumns = [JoinColumn(name = "id_usr")],
            inverseJoinColumns = [JoinColumn(name = "id_rank")]
    )

    var roles: Set<RanksDao> = emptySet()


    }


