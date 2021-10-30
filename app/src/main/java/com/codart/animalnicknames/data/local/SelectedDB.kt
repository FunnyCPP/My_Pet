package com.codart.animalnicknames.data.local

import com.codart.animalnicknames.data.entities.Nickname
import io.paperdb.Paper

object SelectedDB {


        fun addItem(nickname: Nickname) {
            val nicknames = SelectedDB.getMods()
            nicknames.add(nickname)
            try{ SelectedDB.saveMods(nicknames) }
            catch (e: Exception){}

        }

        fun removeItem(nickname: Nickname) {

            val nicknames = SelectedDB.getMods()
            nicknames.remove(nickname)
            SelectedDB.saveMods(nicknames)

        }

        fun saveMods(mods: MutableList<Nickname>) {
            try{ Paper.book().write("nickname", mods) }catch (e:Exception){}
        }

        fun getMods(): MutableList<Nickname> {
            return try{ Paper.book().read("nickname", mutableListOf()) }
            catch (e:Exception){
                mutableListOf<Nickname>()
            }
        }
        fun checkMod(nickname: Nickname): Boolean{
            val nicknames = SelectedDB.getMods()
            return nicknames.contains(nickname)
        }
    }