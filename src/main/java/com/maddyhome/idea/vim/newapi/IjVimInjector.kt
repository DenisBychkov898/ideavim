package com.maddyhome.idea.vim.newapi

import com.intellij.openapi.components.service
import com.intellij.openapi.components.serviceIfCreated
import com.intellij.openapi.diagnostic.Logger
import com.maddyhome.idea.vim.api.EngineEditorHelper
import com.maddyhome.idea.vim.api.ExecutionContextManager
import com.maddyhome.idea.vim.api.VimApplication
import com.maddyhome.idea.vim.api.VimChangeGroup
import com.maddyhome.idea.vim.api.VimDigraphGroup
import com.maddyhome.idea.vim.api.VimEditor
import com.maddyhome.idea.vim.api.VimEnabler
import com.maddyhome.idea.vim.api.VimKeyGroup
import com.maddyhome.idea.vim.api.VimMarkGroup
import com.maddyhome.idea.vim.api.VimMessages
import com.maddyhome.idea.vim.api.VimProcessGroup
import com.maddyhome.idea.vim.api.VimRegisterGroup
import com.maddyhome.idea.vim.api.VimStringParser
import com.maddyhome.idea.vim.api.VimVisualMotionGroup
import com.maddyhome.idea.vim.command.CommandState
import com.maddyhome.idea.vim.common.VimMachine
import com.maddyhome.idea.vim.diagnostic.VimLogger
import com.maddyhome.idea.vim.group.MarkGroup
import com.maddyhome.idea.vim.helper.IjActionExecutor
import com.maddyhome.idea.vim.helper.IjEditorHelper
import com.maddyhome.idea.vim.helper.IjVimStringParser
import com.maddyhome.idea.vim.helper.VimActionExecutor
import com.maddyhome.idea.vim.helper.vimCommandState
import com.maddyhome.idea.vim.options.OptionService

class IjVimInjector : VimInjector {
  override fun <T : Any> getLogger(clazz: Class<T>): VimLogger = IjVimLogger(Logger.getInstance(clazz::class.java))

  override val actionExecutor: VimActionExecutor
    get() = service<IjActionExecutor>()
  override val nativeActionManager: NativeActionManager
    get() = service<IjNativeActionManager>()
  override val messages: VimMessages
    get() = service<IjVimMessages>()
  override val registerGroup: VimRegisterGroup
    get() = service()
  override val registerGroupIfCreated: VimRegisterGroup?
    get() = serviceIfCreated()
  override val changeGroup: VimChangeGroup
    get() = service()
  override val processGroup: VimProcessGroup
    get() = service()
  override val keyGroup: VimKeyGroup
    get() = service()
  override val markGroup: VimMarkGroup
    get() = service<MarkGroup>()
  override val application: VimApplication
    get() = service<IjVimApplication>()
  override val executionContextManager: ExecutionContextManager
    get() = service<IjExecutionContextManager>()
  override val vimMachine: VimMachine
    get() = service<VimMachineImpl>()
  override val enabler: VimEnabler
    get() = service<IjVimEnabler>()
  override val digraphGroup: VimDigraphGroup
    get() = service()
  override val visualMotionGroup: VimVisualMotionGroup
    get() = service()

  override val optionService: OptionService
    get() = service()
  override val parser: VimStringParser
    get() = service<IjVimStringParser>()

  override fun commandStateFor(editor: VimEditor): CommandState {
    var res = editor.ij.vimCommandState
    if (res == null) {
      res = CommandState(editor)
      editor.ij.vimCommandState = res
    }
    return res
  }

  override val engineEditorHelper: EngineEditorHelper
    get() = service<IjEditorHelper>()
}
